package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.project.*;
import preCapstone.fuseable.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 조회
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/{userId}")
    public ProjectReadListDto readProjectList(@PathVariable ("userId") Long userId) {
        return ProjectReadListDto.builder()
                .readProjectList(projectService.projectReadList(userId))
                .build();
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    // 프로젝트 생성
    @PostMapping("/create/{userId}")
    //front로부터 title명 요구
    public ProjectCreateDto createProject(@PathVariable ("userId") Long userId,@RequestBody String title) { //제목 + 로그인된 사용자
        return projectService.createProject(userId, title);
    }

    // 프로젝트 삭제
    //front에서 mapping에 대해 프로젝트를 들어간 후 삭제를 줄건지
    //아니면 project 선택창에 삭제를 줄건지
    //그러면 어떻게 mapping이 되는지에 따라 조금 다를듯
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/delete/{userId}/{projectId}")
    public ProjectDeleteDto deleteProject (@PathVariable ("userId") Long userId,@PathVariable ("projectId") Long projectId){ //프로젝트 Id + 로그인된 사용자
        return projectService.deleteProject(userId, projectId);
    }

    //프로젝트 선택
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/{userId}/{projectId}")
    public ProjectSelectDto selectProject (@PathVariable ("userId") Long userId,@PathVariable ("projectId") Long projectId){
        return projectService.selectProject(projectId, userId);
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("update/{userId}/{projectId}")
    //front로부터 title명 요구
    public ProjectUpdateDto updateProject(@PathVariable ("userId") Long userId,@PathVariable ("projectId") Long projectId, @RequestBody String title) { //제목 + 로그인된 사용자
        return projectService.updateProject(userId, projectId, title);
    }
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/{projectId}/crews")
    public ProjectCrewListDto readCrewList(@PathVariable("projectId") Long projectId){
        return projectService.readCrewList(projectId);
    }

    //북마크 체크
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/bookmark/{userId}/{projectId}")
    public ProjectBookmarkDto bookmarkProject(@PathVariable("projectId") Long projectId,@PathVariable ("userId") Long userId){
        return projectService.bookmarkProject(projectId,userId);
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/invite/{userId}")
    public ProjectInviteDto inviteProject(@PathVariable ("userId") Long userId, @RequestBody ProjectInviteCodeDto inviteCode) {
        return projectService.inviteProject(inviteCode,userId);
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/invite/{userId}/{projectId}")
    public ProjectCreateInviteDto inviteCodeProject(@PathVariable("projectId") Long projectId,@PathVariable ("userId") Long userId){
        return projectService.createInviteCode(projectId,userId);
    }

    // 초대 (보류)
}
