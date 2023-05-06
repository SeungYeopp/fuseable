package preCapstone.fuseable.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CommentListDto {
    private List<CommentReadDto> commentList;

    @Builder
    public CommentListDto(List<CommentReadDto> commentList) {
        this.commentList = commentList;
    }
}
