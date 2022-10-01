package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.project.ProjectDetailForProjectListDto;
import preCapstone.fuseable.dto.project.ProjectEachResponseDto;
import preCapstone.fuseable.model.User;
import preCapstone.fuseable.repository.project.ProjectRepository;
import preCapstone.fuseable.model.project.UserProjectMapping;
import preCapstone.fuseable.repository.project.userProjectMappingRepository;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;  //ProjectRepository final

    @Transactional(readOnly = true)  //읽기만하므로, 속도 빠름
    public List<ProjectEachResponseDto> readProjectList(User currentUser) {  //현재 사용자를 받아서, 사용자의 프로젝트 리스트를 알려줌

        //현재 사용자의 ID를 키값으로 들어가있는 프로젝트 확인 (유저 ID, 프로젝트 순서,프로젝트 ID)
        List<UserProjectMapping> userProjectList = userProjectMappingRepository.findbyUserId(currentUser.getUserCode());

        //Project ID를 모음
        List<Long> projectIdList = userProjectList.stream()
                .map(userProjectMapping -> userProjectMapping.getProject().getProjectId())
                .collect(Collectors.toList());

        //project 세부정보 조회
        List<ProjectDetailForProjectListDto> projectDetail = projectRepository.findProjectDetailForProjectList(projectIdList);

        //
        return projectDetail.stream()
                .map(project -> ProjectEachResponseDto.of(project, userProjectList))
                .collect(Collectors.toList());
    }


}
