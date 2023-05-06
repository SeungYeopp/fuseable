package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import preCapstone.fuseable.model.note.Step;

@Getter
@NoArgsConstructor
public class NoteMoveDetailDto {

    //step, 전 후 id, projectId

    private String newStep;
    private Long arrayId;
    private Long newArrayId;

    @Builder
    public NoteMoveDetailDto (String newStep, Long arrayId, Long newArrayId) {
        this.newStep = newStep;
        this.arrayId = arrayId;
        this.newArrayId = newArrayId;
    }

}
