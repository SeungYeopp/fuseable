package preCapstone.fuseable.dto.note;

import lombok.Builder;
import preCapstone.fuseable.model.note.Note;

import java.time.LocalDate;

public class NoteReadDto {
    private Long noteId;
    private String title;
    private String content;
    private String endAt;

    @Builder
    public NoteReadDto(Long noteId, String title, String content, LocalDate endAt) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.endAt = endAt.toString();
    }
    public static NoteReadDto of (Note note) {
        return NoteReadDto.builder()
                .noteId(note.getNoteId())
                .title(note.getTitle())
                .content(note.getContent())
                .endAt(note.getEndAt())
                .build();
    }
}
