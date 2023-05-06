package preCapstone.fuseable.dto.note;

import lombok.Builder;

public class NoteCommentDetailDto {

    private String comment;

    private Long writerId;

    @Builder
    public NoteCommentDetailDto(String comment, Long writerId) {
        this.comment=comment;
        this.writerId=writerId;
    }
}
