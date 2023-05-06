package preCapstone.fuseable.repository.comment;

import preCapstone.fuseable.model.comment.Comment;

import java.util.List;

public interface CommentRepositoryQuerydsl {
    List<Comment> findByNoteId(Long projectId);

    void deleteByCommentIdAndUserId(Long commentId, Long userId);

    void deleteCommentByNoteId(Long noteId);

//    void deleteCommentByProjectId(Long projectId);
}
