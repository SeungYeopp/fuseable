package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteUpdateDto {
    private Long noteId;

    @Builder
    public NoteUpdateDto(Long noteId) {
        this.noteId = noteId;

    }
}