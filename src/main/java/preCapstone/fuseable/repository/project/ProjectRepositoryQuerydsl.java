package preCapstone.fuseable.repository.project;

import preCapstone.fuseable.dto.project.ProjectDetailDto;
import preCapstone.fuseable.model.project.Project;

import java.util.List;

public interface ProjectRepositoryQuerydsl {

    List<ProjectDetailDto> findByProjectId(List<Long> projectIdList);

    Project findByOneProjectId(Long projectId);
}
