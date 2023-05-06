package preCapstone.fuseable.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryQuerydsl {
}
