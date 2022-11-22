package preCapstone.fuseable.repository.note;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.repository.project.ProjectRepositoryQuerydsl;

public interface NoteRepository extends JpaRepository<Note, Long>, NoteRepositoryQuerydsl {
}
