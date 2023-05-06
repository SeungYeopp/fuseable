package preCapstone.fuseable.repository.comment;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import preCapstone.fuseable.exception.ApiRequestException;
import preCapstone.fuseable.model.comment.Comment;
import preCapstone.fuseable.model.comment.QComment;
import preCapstone.fuseable.model.note.QNote;
import preCapstone.fuseable.model.project.QProject;

import javax.persistence.EntityManager;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    QComment comment = new QComment("c");
    QNote note = new QNote("n");

    @Override
    public List<Comment> findByNoteId(Long noteId) {
        return queryFactory
                .selectFrom(comment)
                .where(comment.note.noteId.eq(noteId))
                .fetch();
    }

    @Override
    public void deleteByCommentIdAndUserId(Long commentId, Long userId) {
        queryFactory
                .delete(comment)
                .where(comment.commentId.eq(commentId), comment.user.userCode.eq(userId))
                .execute();
    }

    @Override
    public void deleteCommentByNoteId(Long noteId) {
        queryFactory
                .delete(comment)
                .where(comment.note.noteId.eq(noteId))
                .execute();
    }

    @Override
    public void deleteCommentByProjectId(Long projectId) {
        queryFactory
                .delete(comment)
                .where(
                        comment.note.noteId.in(
                                JPAExpressions
                                        .select(note.noteId)
                                        .from(note)
                                        .where(note.project.projectId.eq(projectId))
                        )
                )
                .execute();
    }

}
