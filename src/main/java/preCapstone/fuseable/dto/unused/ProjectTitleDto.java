package preCapstone.fuseable.dto.unused;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.user.User;


//Request
@Getter
@NoArgsConstructor
public class ProjectTitleDto {
    private String title;

    private Long userId;

    @Builder
    public ProjectTitleDto(String title, Long userId) {
        this.title = title;
        this.userId = userId;
    }

}
