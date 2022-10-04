package preCapstone.fuseable.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.project.UserProjectMapping;

public interface UserProjectMappingRepository extends JpaRepository<UserProjectMapping, Long>
{
}
