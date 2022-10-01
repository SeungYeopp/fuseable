package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.project.Project;

@Getter
public class ProjectListDto {
    private Long projectId;
    private String title;
    private String detail;

    @Builder
    public ProjectListDto (Long projectId, String title, String detail) {
        this.projectId = projectId;
        this.title = title;
        this.detail = detail;
    }

    public static ProjectListDto fromEntity (Project project) {
        return ProjectListDto.builder()
                .title(project.getTitle())
                .detail(project.getDetail())
                .projectId(project.getProjectId())
                .build();
    }
}
