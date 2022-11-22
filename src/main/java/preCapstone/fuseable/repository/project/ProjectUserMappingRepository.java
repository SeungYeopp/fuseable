package preCapstone.fuseable.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;

import java.util.List;

public interface ProjectUserMappingRepository extends JpaRepository<ProjectUserMapping, Long>, ProjectUserMappingRepositoryQuerydsl {

}