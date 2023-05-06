package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.dto.file.FileDetailDto;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.note.Step;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NoteUpdateDto {
    private Long arrayId;

    @Builder
    public NoteUpdateDto(Long arrayId) {
        this.arrayId = arrayId;

    }
}
