package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import preCapstone.fuseable.dto.file.FileDetailDto;
import preCapstone.fuseable.model.note.Step;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoteCreateDetailDto {

    //note 생성이 필요한 정보들
    //note Id, 제목, 내용, 끝나는 날짜, 스텝
    private Long arrayId;
    private String step;

    private String title;

    private String content;

    private String endAt;

    private List<FileDetailDto> files;

    //endAt과 step은 각각 localdate, step 이었기때문에 string으로 바꿔서 넣어줌
    @Builder
    public NoteCreateDetailDto(Long arrayId,  String step, String title, String content, String endAt, List<FileDetailDto> files) {
        this.arrayId = arrayId;
        this.step = step;
        this.title = title;
        this.content = content;
        this.endAt = endAt;
        this.files = files;
    }
}
