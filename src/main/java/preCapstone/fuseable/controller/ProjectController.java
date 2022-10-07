package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.project.ProjectCreateDto;
import preCapstone.fuseable.dto.project.ProjectDeleteDto;
import preCapstone.fuseable.dto.project.ProjectReadListDto;
import preCapstone.fuseable.dto.project.ProjectTitleDto;
import preCapstone.fuseable.model.oauth.UserDetailsImpl;
import preCapstone.fuseable.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 조회
    @GetMapping("")
    public ProjectReadListDto projectReadList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ProjectReadListDto.builder()
                .projectReadList(projectService.projectReadList(userDetails.getUser()))
                .build();
    }

    // 프로젝트 생성
    @PostMapping("")
    public ProjectCreateDto createProject(@RequestBody ProjectTitleDto TitleDto, @AuthenticationPrincipal UserDetailsImpl userDetails) { //제목 + 로그인된 사용자
        return projectService.createProject(TitleDto, userDetails.getUser());
    }

    // 프로젝트 삭제
    @DeleteMapping("/{projectId}")
    public ProjectDeleteDto deleteProject (@PathVariable("projectId") Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails){ //프로젝트 Id + 로그인된 사용자
        return projectService.deleteProject(projectId, userDetails.getUser());
    }

    //프로젝트 선택
    @GetMapping("/{projectId}")
    public ProjectSelectDto selectProject (@PathVariable Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return projectService.selectProject(projectId, userDetails.getUser())
    }

    // 초대 (보류)
}
