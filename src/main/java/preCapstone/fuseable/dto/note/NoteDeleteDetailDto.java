package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteDeleteDetailDto {

    private Long arrayId;

    @Builder
    public NoteDeleteDetailDto(Long arrayId, Long projectId) {
        this.arrayId = arrayId;
    }
}
