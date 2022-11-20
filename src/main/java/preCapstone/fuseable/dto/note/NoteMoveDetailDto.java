package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Step;

@Getter
public class NoteMoveDetailDto {

    //step, 전 후 id, projectId

    private Step newStep;
    private Long arrayId;
    private Long newArrayId;

    @Builder
    public NoteMoveDetailDto (Step newStep, Long arrayId, Long newArrayId) {
        this.newStep = newStep;
        this.arrayId = arrayId;
        this.newArrayId = newArrayId;
    }

}
