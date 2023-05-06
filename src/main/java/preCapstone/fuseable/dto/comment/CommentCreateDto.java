package preCapstone.fuseable.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateDto {
    private Long commentId;
    private String content;
    private Long writer;

    @Builder
    public CommentCreateDto(Long commentId, String content, Long writer) {
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
    }
}
