package preCapstone.fuseable.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteDto {
    private Long commentId;

    @Builder
    public CommentDeleteDto(Long commentId) {
        this.commentId = commentId;
    }
}
