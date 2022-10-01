package preCapstone.fuseable.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import preCapstone.fuseable.dto.project.ProjectListDto;
import preCapstone.fuseable.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService
    // 프로젝트 조회
    @GetMapping("")
    public ProjectListDto readProjectList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ProjectListDto.builder()
                .build();

    }

    // 프로젝트 생성

    // 프로젝트 수정

    // 프로젝트 삭제

    // 초대 (보류)
}
