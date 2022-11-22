package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectUpdateDto {

    private String title;

    @Builder
    public ProjectUpdateDto(String title) {
        this.title = title;
    }



    public static ProjectUpdateDto of(String title) {

        return ProjectUpdateDto.builder()
                .title(title)
                .build();
    }
}
