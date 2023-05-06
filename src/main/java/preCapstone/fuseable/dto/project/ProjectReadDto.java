package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.project.Role;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProjectReadDto {

    // 프로젝트 목록 조회시 사용되는 DTO, 기본적인 정보만 넣어놨지만 필요하면 추가해야하므로 따로 분리함
    //service에서 사용, 프론트에 줘야할 것은 프로젝트 ID / title / 관리용 업데이트 시간 / 권한 정도

    // 프로젝트 id
    private Long projectId;

    // 프로젝트 명
    private String title;

    // 프로젝트 안에 노트 중 가장 최근 수정 일자
    private LocalDateTime recentNoteUpdateDate;

    private Boolean admin;

    private Boolean bookmark;

    @Builder
    public ProjectReadDto(Long projectId, String title,  LocalDateTime recentNoteUpdateDate,Boolean admin, Boolean bookmark) {
        this.projectId = projectId;
        this.title = title;
        this.recentNoteUpdateDate = recentNoteUpdateDate;
        this.admin = admin;
        this.bookmark = bookmark;
    }

    //of를 통해 특정 객체를 요소를 갖는 stream
    //여기서는 ProjectDeatilDto와 List<ProjectUserMapping>
    public static ProjectReadDto of(ProjectDetailDto projectTotal, List<ProjectUserMapping> projectUserMappingList){

        // projectusermappinglist를 stream을 통해 돌림
        //그 중 stream id가 service의 Read의 해당 유저의 id 리스트와 같은게 있는지 filter로 확인
        //findAny를 통해 같은를 찾음
        //orElse부분은 비어있는 경우 넣는 값인데... 일단은 보류,보험용
        //projectusermapping에 role부분을 직접 넣어주는 작업임
        ProjectUserMapping projectUserMapping = projectUserMappingList.stream()
                .filter(userProject-> userProject.getProject().getProjectId().equals(projectTotal.getProjectId()))
                .findAny()
                .orElse(ProjectUserMapping.builder()
                        .role(Role.ROLE_USER)
                        .build());

        //위의 user인 경우 넣어주는 것을 더해서 최종적인 build
        //projectID /title/recentNoteupdate/admin을 넘겨줌
        //service에서는 이 부분이 list로 형성되어 넘겨지게됨
        return ProjectReadDto.builder()
                .projectId(projectTotal.getProjectId())
                .title(projectTotal.getTitle())
                .recentNoteUpdateDate(projectTotal.getRecentNoteUpdateDate())
                // master = 1 이면 삭제 권한있음
                .admin(projectUserMapping.getRole()==Role.ROLE_ADMIN)
                .build();
    }
}
