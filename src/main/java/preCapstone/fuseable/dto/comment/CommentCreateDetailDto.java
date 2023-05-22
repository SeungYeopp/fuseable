package preCapstone.fuseable.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateDetailDto {

    private String content;
    private Long writerId;

    @Builder
    public CommentCreateDetailDto(String content, Long writerId) {
        this.content = content;
        this.writerId = writerId;
    }
}