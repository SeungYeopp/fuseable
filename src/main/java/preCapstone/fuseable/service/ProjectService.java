package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.project.*;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.project.ProjectRepository;
import preCapstone.fuseable.repository.project.ProjectUserMappingRepository;


import java.util.List;
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
        //Usercode(ID)를 통해서 현재 해당 유저가 매핑되어있는 프로젝트 프로젝트 ID/Role을 받음. Seq을 통한
        List<ProjectUserMapping> projectUserMapping = ProjectUserMappingRepository.findbyUserId(currentUser.getUserCode());

        //위의 내용을 기반으로 루프를 통하여 해당 유저의 프로젝트 ID를 모음
        List<Long> projectIdList = projectUserMapping.stream()
                .map(projectUserMapping -> projectUserMapping.getProject().getProjectId())
                .collect(Collectors.toList());

        //project id 리스트를 통하여 ID와 Title를 projectTotalList에 넣어주면 됨
        List<ProjectTotalList> projectTotalList = projectRepository.findProjectIDandTitle(projectIdList);

        //
        return projectDetail.stream()
                .map(project -> ProjectReadDto.of(project))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectCreateDto createProject(ProjectTitleDto TitleDto, User currentUser) {

        // 프로젝트 생성
        Project project = projectRepository.save(Project.toEntity(TitleDto));

        // 유저-프로젝트 테이블에도 저장 , 현재 유저와 프로젝트를 요구로하므로 이 사항들을 build해줌
        ProjectUserMapping projectUserMapping = ProjectUserMapping.builder()
                .user(currentUser)
                .project(project)
                .build();

        //해당 내용을 Repository에 저장 (user id - project id)
        projectUserMappingRepository.save(projectUserMapping);

        return ProjectCreateDto.fromEntity(project);
    }

    @Transactional
    public ProjectDeleteDto deleteProject(Long projectId, User currentUser) {

        ProjectUserMapping projectUserMapping = ProjectUserMappingRepository.findbyUserId(currentUser.getUsercode());

        // UserProjectMapping 테이블에서 삭제
        projectUserMapping.deleteByProjectId(projectId);

        // Project 테이블에서 Project 삭제
        projectRepository.deleteById(projectId);

        return ProjectDeleteDto.builder()
                .projectId(projectId)
                .build();
    }

    @Transactional
    public ProjectSelectDto selectDto(Long projectId, User CurrentUser){

        return
    }

}
