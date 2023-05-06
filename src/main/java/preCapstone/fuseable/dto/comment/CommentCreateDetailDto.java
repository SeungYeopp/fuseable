package preCapstone.fuseable.dto.comment;

import lombok.Builder;

public class CommentCreateDetailDto {

    private String comment;

    private Long writerId;

    @Builder
    public CommentCreateDetailDto(String comment, Long writerId) {
        this.comment=comment;
        this.writerId=writerId;
    }
}
