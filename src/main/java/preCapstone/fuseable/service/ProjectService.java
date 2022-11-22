package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.project.*;
import preCapstone.fuseable.exception.ApiRequestException;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.project.Role;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.file.FileRepository;
import preCapstone.fuseable.repository.note.NoteRepository;
import preCapstone.fuseable.repository.project.ProjectRepository;
import preCapstone.fuseable.repository.project.ProjectUserMappingRepository;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static preCapstone.fuseable.model.note.QNote.note;


@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;  //ProjectRepository final

    private final ProjectUserMappingRepository projectUserMappingRepository;

    private final NoteRepository noteRepository;

    private final FileRepository fileRepository;

    @Transactional(readOnly = true)  //읽기만하므로, 속도 빠름
    public List<ProjectReadDto> projectReadList(User currentUser) {  //현재 사용자를 받아서, 사용자의 프로젝트 리스트를 알려줌

        //현재 사용자의 project-user간의 매핑 확인하여 리스트로 만듬
        //Usercode(ID)를 통해서 현재 해당 유저가 매핑되어있는 프로젝트(타이틀과 프로젝트id)/해당 프로젝트의 Role을 받음. Seq을 통한 정리
        List<ProjectUserMapping> projectUserMappingList = projectUserMappingRepository.findByUserId(currentUser.getUserCode());

        //위에서 얻은 프로젝트에 대해 프로젝트 Id만을 루프를 통하여 모음
        List<Long> projectIdList = projectUserMappingList.stream()
                .map(projectUserMapping -> projectUserMapping.getProject().getProjectId())
                .collect(Collectors.toList());

        //project id 리스트를 통하여 ID와 Title를 projectTotalList에 넣어주면 됨, recenttime은 추가로 들어감
        List<ProjectDetailDto> projectDetail = projectRepository.findByProjectId(projectIdList);

        //projectDetail을 stream으로 돌림, map을 통해 projectDetail을 project라고 하고
        //projectReadDto.of의 내용으로 변환. 이때 사용들어가야할 내용은
        //projectId,title,recentNoteUpdateDate,admin
        //이것을 뽑아내서 list로 만들어 return을 해주는 것
        return projectDetail.stream()
                .map(project ->
                        ProjectReadDto
                                .of(project, projectUserMappingList)
                ).collect(Collectors.toList());
    }

    @Transactional
    public ProjectCreateDto createProject(ProjectTitleDto titleDto, User currentUser) {

        //titleDto의 toEntity를 통해 제목을 projectRepository에 저장
        //그 이후 project안에 넣음
        Project project = projectRepository.save(Project.toEntity(titleDto));

        // 프로젝트와 유저 매핑 테이블에도 저장 , 현재 user와 project, Role을 요구로하므로 이 사항들을 build해줌
        ProjectUserMapping projectUserMapping = ProjectUserMapping.builder()
                .role(Role.ROLE_ADMIN)  //최초 프로젝트 만든사람 == ADMIN
                .user(currentUser)      //현재 유저 정보
                .project(project)       //프로젝트 (ID + Title)
                .build();

        //해당 내용을 ProjectUserMappingRepository에 저장
        projectUserMappingRepository.save(projectUserMapping);

        //생성된 새로운 프로젝트를 반환
        return ProjectCreateDto.fromEntity(project);
    }

    @Transactional
    public ProjectDeleteDto deleteProject(Long projectId, User currentUser) {

        //유저가 해당 project에 있는지 확인 후 role/user/projectid/project title등 획득
        //optional 제거
        Optional<ProjectUserMapping> projectUserMapping
                = projectUserMappingRepository.findByUserIdAndProjectId(currentUser.getUserCode(), projectId);

        // Project의 Admin인지 확인 권한 확인

        if (!projectUserMapping.isPresent()) { //   우선 해당 프로젝트의 CREW이기라도 한지.
            throw new ApiRequestException("프로젝트 소유주가 아닙니다.");
        }
        else if (!projectUserMapping.get().getRole().equals(Role.ROLE_ADMIN)) {
            throw new ApiRequestException("Admin 권한이 없습니다.");
        }

        // Project 테이블에서 Project 삭제
        projectRepository.deleteById(projectId);

        // Project-User간의 관계에서도 projectId를 기준으로 삭제한다.
        projectUserMappingRepository.deleteByProjectId(projectId);

        noteRepository.deleteNoteByProjectId(projectId);

        fileRepository.deleteFileByProjectId(projectId);

        //추후에 필요한 repository 삭제 목록 쓰기


        //일 다하고 return
        return ProjectDeleteDto.builder()
                .projectId(projectId)
                .build();
    }


    @Transactional(readOnly = true)
    public ProjectSelectDto selectProject(Long projectId, User currentUser){

        //projectId를 통해 Crew원의 id들을 찾고 이를 ListDto로 묶음
        //ProjectCrewListDto crewList = projectUserMappingRepository.findCrewByProjectId(projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApiRequestException("회원을 조회할 프로젝트가 존재하지 않습니다."));

        List<ProjectUserMapping> userProjectMapping = projectUserMappingRepository.findAllByProject(project);

        //크루원의 유저id/카카오닉네임/카카오사진을 list로 주게됨
        List<ProjectCrewDto> crewList = userProjectMapping.stream().map(
                        crew -> ProjectCrewDto.builder()
                                .userId(crew.getUser().getUserCode())
                                .userName(crew.getUser().getKakaoNickname())
                                .userPicture(crew.getUser().getKakaoProfileImg())
                                .build())
                .collect(Collectors.toList());

        //role을 받기위한것
        Optional<ProjectUserMapping> projectUserMapping
                = projectUserMappingRepository.findByUserIdAndProjectId(currentUser.getUserCode(), projectId);

        //note에 관한 부분 넣기, kanban 연계
        List<Note> noteList = noteRepository.findAllByProjectId(projectId);

        //ArrayId 기준으로 정렬
        List<Note> sortedList = noteList.stream()
                .sorted(Comparator.comparing(Note::getArrayId)).toList();

        //총 title/role/crew/note이 들어가게 되는것
        return ProjectSelectDto.fromEntity(crewList, projectUserMapping, sortedList);
    }

    @Transactional
    public ProjectUpdateDto updateProject(Long projectId, ProjectTitleDto titleDto, User currentUser) {

        //유저가 해당 project에 있는지 확인 후 role/user/projectid/project title등 획득
        Optional<ProjectUserMapping> projectUserMapping
                = projectUserMappingRepository.findByUserIdAndProjectId(currentUser.getUserCode(), projectId);

        // Project의 Admin인지 확인 권한 확인
        if (!projectUserMapping.isPresent()) { //   우선 해당 프로젝트의 CREW이기라도 한지.
            throw new ApiRequestException("프로젝트 소유주가 아닙니다.");
        }
        else if (!projectUserMapping.get().getRole().equals(Role.ROLE_ADMIN)) {
            throw new ApiRequestException("Admin 권한이 없습니다.");
        }

        projectUserMapping.get().getProject().updateTitle(titleDto.getTitle());


        //project 가져오기
        Project project = projectRepository.findByOneProjectId(projectId);
        project.updateTitle(titleDto.getTitle());


        //생성된 새로운 프로젝트를 반환
        return ProjectUpdateDto.builder()
                .title(projectUserMapping.get().getProject().getTitle())
                .build();
    }

}
