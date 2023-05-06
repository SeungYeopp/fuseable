package preCapstone.fuseable.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.note.NoteComment;

public interface CommentRepository extends JpaRepository<NoteComment, Long>, CommentRepositoryQuerydsl {
}
