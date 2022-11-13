package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoteDeleteDto {
    private Long noteId;

    @Builder
    public NoteDeleteDto(Long noteId) {

        this.noteId = noteId;
    }

}
