package preCapstone.fuseable.dto.comment;

import lombok.Builder;
import preCapstone.fuseable.model.comment.Comment;

import java.time.LocalDateTime;

public class CommentReadDto {
    private Long commentId;
    private String content;
    private Long writer;

    @Builder
    public CommentReadDto(Long commentId, String content, Long writer) {
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
    }

    public static CommentReadDto fromEntity(Comment comment){
        return CommentReadDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getComment())
                .writer(comment.getUser().getUserCode())
                .build();

    }
}
