package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.project.*;
import preCapstone.fuseable.model.oauth.UserDetailsImpl;
import preCapstone.fuseable.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 조회
    @GetMapping("")
    public ProjectReadListDto readProjectList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ProjectReadListDto.builder()
                .readProjectList(projectService.projectReadList(userDetails.getUser()))
                .build();
    }

    // 프로젝트 생성
    @PostMapping("")
    //front로부터 title명 요구
    public ProjectCreateDto createProject(@RequestBody ProjectTitleDto TitleDto, @AuthenticationPrincipal UserDetailsImpl userDetails) { //제목 + 로그인된 사용자
        return projectService.createProject(TitleDto, userDetails.getUser());
    }

    // 프로젝트 삭제
    //front에서 mapping에 대해 프로젝트를 들어간 후 삭제를 줄건지
    //아니면 project 선택창에 삭제를 줄건지
    //그러면 어떻게 mapping이 되는지에 따라 조금 다를듯
    @DeleteMapping("/{projectId}")
    public ProjectDeleteDto deleteProject (@PathVariable Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails){ //프로젝트 Id + 로그인된 사용자
        return projectService.deleteProject(projectId, userDetails.getUser());
    }

    //프로젝트 선택
    @GetMapping("/{projectId}")
    public ProjectSelectDto selectProject (@PathVariable Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return projectService.selectProject(projectId, userDetails.getUser());
    }

    // 초대 (보류)
}
