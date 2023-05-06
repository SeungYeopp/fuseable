package preCapstone.fuseable.repository.note;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.note.NoteComment;

public interface NoteCommentRepository extends JpaRepository<NoteComment, Long>, NoteCommentRepositoryQuerydsl {
}
