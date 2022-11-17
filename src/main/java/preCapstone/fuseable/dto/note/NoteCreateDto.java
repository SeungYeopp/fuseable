package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.dto.file.FileDetailDto;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.note.Step;

import java.time.LocalDate;
import java.util.List;

@Getter
public class NoteCreateDto {

    //note 생성이 필요한 정보들
    //note Id, 제목, 내용, 끝나는 날짜, 스텝
    private Long noteId;
    private String title;
    private String content;
    private String endAt;
    private String step;

    private List<FileDetailDto> files;

    @Builder
    public NoteCreateDto(Long noteId, String title, String content, LocalDate endAt, Step step) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.endAt = endAt.toString();
        this.step = step.toString();
    }

    public static NoteCreateDto fromEntity(Note note) {
        return NoteCreateDto.builder()
                .noteId(note.getNoteId())
                .title(note.getTitle())
                .content(note.getContent())
                .endAt(note.getEndAt())
                .step(note.getStep())
                .build();
    }

    //file 업로드 관련
    public void uploadFile(List<FileDetailDto> files) {

        this.files = files;
    }
}