package preCapstone.fuseable.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import preCapstone.fuseable.model.comment.Comment;

import java.util.List;

@NoArgsConstructor
@Getter
public class CommentListDto {
    private List<Comment> commentList;

    @Builder
    public CommentListDto(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void of(List<Comment> commentList) {
        this.commentList = commentList;
    }
}