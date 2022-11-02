package preCapstone.fuseable.repository.project;

import preCapstone.fuseable.dto.project.ProjectDetailDto;

import java.util.List;

public interface ProjectRepositoryQuerydsl {

    List<ProjectDetailDto> findByProjectId(List<Long> projectIdList);
}
