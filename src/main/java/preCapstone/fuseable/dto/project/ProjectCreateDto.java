package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.project.Project;

@Getter
public class ProjectCreateDto {
    private Long projectId;
    private String title;

    @Builder
    public ProjectCreateDto(Long projectId, String title) {
        this.projectId = projectId;
        this.title = title;
    }

    public static ProjectCreateDto fromEntity (Project project) {
        return ProjectCreateDto.builder()
                .title(project.getTitle())
                .projectId(project.getProjectId())
                .build();
    }
}
