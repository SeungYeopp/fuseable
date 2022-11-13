package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Step;

import java.util.List;

@Getter
public class NoteStepReadDto {
    private final String step;
    private final List<NoteReadDto> notes;

    @Builder
    public NoteStepReadDto(Step step, List<NoteReadDto> kanbanNoteEachResponseDtoList) {
        this.step = step.toString();
        this.notes = kanbanNoteEachResponseDtoList;
    }

    public static NoteStepReadDto of (Step step, List<NoteReadDto> kanbanNoteEachResponseDtoList) {
        return NoteStepReadDto.builder()
                .step(step)
                .kanbanNoteEachResponseDtoList(kanbanNoteEachResponseDtoList)
                .build();
    }
}
