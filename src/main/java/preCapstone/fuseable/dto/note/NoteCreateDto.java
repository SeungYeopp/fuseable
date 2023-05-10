package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.dto.file.FileUploadDetailDto;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.note.Step;

import java.time.LocalDate;
import java.util.List;

@Getter
public class NoteCreateDto {

    //note 생성이 필요한 정보들
    //note Id, 제목, 내용, 끝나는 날짜, 스텝
    private Long arrayId;
    private String title;
    private String content;
    private String endAt;
    private String step;


    private List<FileUploadDetailDto> files;

    //endAt과 step은 각각 localdate, step 이었기때문에 string으로 바꿔서 넣어줌
    @Builder
    public NoteCreateDto(Long arrayId, String title, String content, LocalDate endAt, Step step) {
        this.arrayId = arrayId;
        this.title = title;
        this.content = content;
        this.endAt = endAt.toString();
        this.step = step.toString();
    }

    public void uploadFile(List<FileUploadDetailDto> files) {
        this.files = files;
    }


    public static NoteCreateDto fromEntity(Note note) {
        return NoteCreateDto.builder()
                .arrayId(note.getArrayId())
                .title(note.getTitle())
                .content(note.getContent())
                .endAt(note.getEndAt())
                .step(note.getStep())
                .build();
    }
}