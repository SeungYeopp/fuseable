package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.project.Project;


//Request
@Getter
public class ProjectTitleDto {
    private String title;

    @Builder
    public ProjectTitleDto(String title) { this.title = title; }

}
