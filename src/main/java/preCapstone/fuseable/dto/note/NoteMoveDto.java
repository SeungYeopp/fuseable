package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Note;

import java.util.List;

@Getter
public class NoteMoveDto {

    private List<Note> note;

    @Builder
    public NoteMoveDto(List<Note> note) {
        this.note = note;
    }
}
