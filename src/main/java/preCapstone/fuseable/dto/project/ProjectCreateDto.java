package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.project.Project;

@Getter
public class ProjectCreateDto {
    private Long projectId;
    private String title;
    private Boolean bookmark;

    @Builder
    public ProjectCreateDto(Long projectId, String title, Boolean bookmark) {
        this.projectId = projectId;
        this.title = title;
        this.bookmark = bookmark;
    }

    //bookmark default는 bookmark 안되어있기
    public static ProjectCreateDto fromEntity (Project project) {
        return ProjectCreateDto.builder()
                .title(project.getTitle())
                .projectId(project.getProjectId())
                .build();
    }
}
