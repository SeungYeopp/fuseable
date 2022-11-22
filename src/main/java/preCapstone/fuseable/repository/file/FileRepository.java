package preCapstone.fuseable.repository.file;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.file.File;

public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryQuerydsl {

}
