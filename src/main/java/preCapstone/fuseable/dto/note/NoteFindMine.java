package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Note;

import java.util.List;

@Getter
public class NoteFindMine {

    //페이지 넘버같은 부분은 front와 협의
    private List<Note> note;

    @Builder
    public NoteFindMine(List<Note> note) {
        this.note = note;
    }
}
