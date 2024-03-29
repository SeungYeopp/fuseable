package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.dto.project.ProjectCrewDto;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.project.Role;

import java.time.LocalDate;
import java.util.List;

@Getter
public class NoteDeleteDto {
    private List<Note> note;

    @Builder
    public NoteDeleteDto(List<Note> note) {
        this.note = note;
    }

}
