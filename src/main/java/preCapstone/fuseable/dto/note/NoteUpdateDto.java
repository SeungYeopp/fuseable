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
    private Long noteId;
    private String title;
    private String content;

    private String endAt;
    private String step;

    private List<FileDetailDto> files;

    @Builder
    public NoteUpdateDto(Long noteId, String title, String content, LocalDate endAt, Step step) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.endAt = endAt.toString();
        this.step = step.toString();
    }

    public void uploadFile(List<FileDetailDto> files) {
        this.files = files;
    }

    public static NoteUpdateDto of(Note note) {
        return NoteUpdateDto.builder()
                .noteId(note.getNoteId())
                .title(note.getTitle())
                .content(note.getContent())
                .endAt(note.getEndAt())
                .step(note.getStep())
                .build();
    }

}