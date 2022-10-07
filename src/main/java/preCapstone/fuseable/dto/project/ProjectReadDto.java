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
    //service에서 사용, 프론트에 줘야할 것은 프로젝트 ID / title / 관리용 업데이트 시간정도

    // 프로젝트 id
    private Long projectId;

    // 프로젝트 명
    private String title;

    // 프로젝트 안에 노트 중 가장 최근 수정 일자
    private LocalDateTime recentNoteUpdateDate;

    private Boolean master;

    @Builder
    public ProjectReadDto(Long projectId, String title,  LocalDateTime recentNoteUpdateDate,Boolean master) {
        this.projectId = projectId;
        this.title = title;
        this.recentNoteUpdateDate = recentNoteUpdateDate;
        this.master = master;
    }
    public static ProjectReadDto of(ProjectDetailDto projectTotal, List<ProjectUserMapping> projectUserMappingList){

        // 유저와 해당 프로젝트의 관계 확인
        ProjectUserMapping projectUserMapping = projectUserMappingList.stream()
                .filter(userProject-> userProject.getProject().getProjectId().equals(projectTotal.getProjectId()))
                .findAny()
                .orElse(ProjectUserMapping.builder()
                        .role(Role.ROLE_USER)
                        .build());

        return ProjectReadDto.builder()
                .projectId(projectTotal.getProjectId())
                .title(projectTotal.getTitle())
                .recentNoteUpdateDate(projectTotal.getRecentNoteUpdateDate())
                // maser = 1 이면 삭제 권한있음
                .master(projectUserMapping.getRole()==Role.ROLE_ADMIN)
                .build();
    }
}
