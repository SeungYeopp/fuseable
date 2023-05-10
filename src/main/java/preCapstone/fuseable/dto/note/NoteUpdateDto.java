package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteUpdateDto {
    private Long arrayId;

    @Builder
    public NoteUpdateDto(Long arrayId) {
        this.arrayId = arrayId;

    }
}
