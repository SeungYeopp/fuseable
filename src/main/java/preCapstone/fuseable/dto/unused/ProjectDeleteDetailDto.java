package preCapstone.fuseable.dto.unused;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectDeleteDetailDto {

    private Long projectId;

    private  Long userId;
    @Builder
    public ProjectDeleteDetailDto(Long projectId, Long userId) {
        this.projectId = projectId;
        this.userId = userId;
    }
}
