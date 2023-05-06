package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteCommentDto {

    //arrayId를 줘야 front에서 넣을 수 있음
    private Long arrayId;

    private String comment;

    private Long writerId;

    @Builder
    public NoteCommentDto(Long arrayId,String comment, Long writerId) {
        this.arrayId=arrayId;
        this.comment=comment;
        this.writerId=writerId;
    }
}
