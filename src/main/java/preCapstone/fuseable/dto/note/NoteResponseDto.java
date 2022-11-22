package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.note.Step;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class NoteResponseDto {
    private Long noteId;

    private Long arrayId;
    private String title;
    private String content;
    private String endAt;
    private String step;
    private Long projectId;

    private String writer;
    private String projectTitle;



    public NoteResponseDto(Long noteId, Long arrayId, String title, String content, LocalDate endAt, Step step,
                           Long projectId, String projectTitle, String writer)
    {
        this.noteId = noteId;
        this.arrayId = arrayId;
        this.title = title;
        this.content = content;
        this.endAt = endAt.toString();
        this.step = step.toString();
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.writer = writer;
    }


    @Builder
    public NoteResponseDto(Long noteId, String title, String content, LocalDate endAt, Step step) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.endAt = endAt.toString();
        this.step = step.toString();
    }

    public static NoteResponseDto of (Note note) {
        return NoteResponseDto.builder()
                .noteId(note.getNoteId())
                .title(note.getTitle())
                .content(note.getContent())
                .endAt(note.getEndAt())
                .step(note.getStep())
                .build();
    }}
