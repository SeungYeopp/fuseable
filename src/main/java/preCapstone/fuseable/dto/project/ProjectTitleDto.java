package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;


//Request
@Getter
public class ProjectTitleDto {
    private String title;

    @Builder
    public ProjectTitleDto(String title) { this.title = title; }
}
