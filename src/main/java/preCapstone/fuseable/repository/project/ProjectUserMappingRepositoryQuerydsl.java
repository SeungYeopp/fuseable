package preCapstone.fuseable.repository.project;

import org.springframework.data.jpa.repository.Modifying;
import preCapstone.fuseable.model.project.ProjectUserMapping;

import java.util.List;
import java.util.Optional;

public interface ProjectUserMappingRepositoryQuerydsl {

    List<ProjectUserMapping> findByUserId(Long userId);

    Optional<ProjectUserMapping> findByUserIdAndProjectId(Long userId, Long projectId);

    @Modifying(clearAutomatically = true)
    void deleteByProjectId(Long projectId);
}
