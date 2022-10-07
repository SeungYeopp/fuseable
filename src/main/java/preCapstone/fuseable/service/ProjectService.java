package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.project.*;
import preCapstone.fuseable.exception.ApiRequestException;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.project.Role;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.project.ProjectRepository;
import preCapstone.fuseable.repository.project.ProjectUserMappingRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;  //ProjectRepository final

    private final ProjectUserMappingRepository projectUserMappingRepository;

    @Transactional(readOnly = true)  //읽기만하므로, 속도 빠름
    public List<ProjectReadDto> projectReadList(User currentUser) {  //현재 사용자를 받아서, 사용자의 프로젝트 리스트를 알려줌

        //현재 사용자의 project-user간의 매핑 확인하여 리스트로 만듬
        //Usercode(ID)를 통해서 현재 해당 유저가 매핑되어있는 프로젝트(타이틀과 프로젝트id)/해당 프로젝트의 Role을 받음. Seq을 통한 정리
        List<ProjectUserMapping> projectUserMappingList = ProjectUserMappingRepository.findbyUserId(currentUser.getUserCode());

        //위에서 얻은 프로젝트에 대해 프로젝트 Id만을 루프를 통하여 모음
        List<Long> projectIdList = projectUserMappingList.stream()
                .map(projectUserMapping -> projectUserMapping.getProject().getProjectId())
                .collect(Collectors.toList());

        //project id 리스트를 통하여 ID와 Title를 projectTotalList에 넣어주면 됨, recenttime은 추가로 들어감
        List<ProjectDetailDto> projectDetail = projectRepository.findbyProjectId(projectIdList);

        return projectDetail.stream()
                .map(project ->
                        ProjectReadDto
                                .of(project, projectUserMappingList)
                ).collect(Collectors.toList());
    }

    @Transactional
    public ProjectCreateDto createProject(ProjectTitleDto titleDto, User currentUser) {

        // 프로젝트 생성하고 project에 담음
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

        ProjectUserMapping projectUserMapping = projectUserMappingRepository.findByUserIdAndProjectId(currentUser.getUserCode(), projectId);

        // Project의 Admin인지 확인 권한 확인
        if (!projectUserMapping.getRole().equals(Role.ROLE_ADMIN)) {
            throw new ApiRequestException("프로젝트 소유주가 아닙니다.");
        }

        ProjectUserMapping projectUserMapping = ProjectUserMappingRepository.findbyUserId(currentUser.getUserCode());

        // UserProjectMapping 테이블에서 삭제
        projectUserMapping.deleteByProjectId(projectId);

        // Project 테이블에서 Project 삭제
        projectRepository.deleteById(projectId);

        //추후에 필요한 repository 삭제 목록 쓰기

        return ProjectDeleteDto.builder()
                .projectId(projectId)
                .build();
    }

    @Transactional
    public ProjectSelectDto selectDto(Long projectId, User CurrentUser){

        return
    }

}
