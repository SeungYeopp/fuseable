package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NoteKanbanReadDto {
    private List<NoteStepReadDto> kanban;

    @Builder
    public NoteKanbanReadDto(List<NoteStepReadDto> kanban) {
        this.kanban = kanban;
    }
}
