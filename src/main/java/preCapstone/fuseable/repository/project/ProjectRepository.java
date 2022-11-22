package preCapstone.fuseable.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.project.Project;

import javax.persistence.EntityManager;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryQuerydsl {
}
