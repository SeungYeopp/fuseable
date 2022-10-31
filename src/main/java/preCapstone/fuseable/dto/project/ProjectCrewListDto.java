package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProjectCrewListDto {
    private Long projectId;
    private List<ProjectCrewDto> crews;

    @Builder
    public ProjectCrewListDto(Long projectId, List<ProjectCrewDto> crews) {
        this.projectId = projectId;
        this.crews = crews;
    }
}
