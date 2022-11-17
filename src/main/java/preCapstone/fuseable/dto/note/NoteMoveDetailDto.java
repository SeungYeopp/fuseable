package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Step;

@Getter
public class NoteMoveDetailDto {

    //옮겨질 곳의 step, 전 후 id
    private Step newStep;
    private Long noteId;
    private Long newNoteId;

    @Builder
    public NoteMoveDetailDto (Step newStep, Long noteId, Long newNoteId) {
        this.newStep = newStep;
        this.noteId = noteId;
        this.newNoteId = newNoteId;
    }

}
