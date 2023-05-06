package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Note;

import java.util.List;

@Getter
public class NoteMoveDto {

    private Long newArrayId;

    @Builder
    public NoteMoveDto(Long newArrayId) {
        this.newArrayId= newArrayId;
    }
}
