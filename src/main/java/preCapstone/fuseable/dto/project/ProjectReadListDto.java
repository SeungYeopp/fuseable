package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import java.util.List;


@Getter
public class ProjectReadListDto {

    List<ProjectReadDto> projects;

    @Builder
    public ProjectReadListDto(List<ProjectReadDto> projectReadList) {
        this.projects = projectReadList;
    }
}
