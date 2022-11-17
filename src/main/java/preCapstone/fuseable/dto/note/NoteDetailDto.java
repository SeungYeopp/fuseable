package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import preCapstone.fuseable.dto.file.FileDetailDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoteDetailDto {

    //note의 관한 정보로 제목/내용/마감기한/스텝/파일
    private String title;
    private String content;
    private String endAt;
    private String step;
    private List<FileDetailDto> files;

    @Builder
    public NoteDetailDto(String title, String content, String endAt, String step,  List<FileDetailDto> files) {
        this.title = title;
        this.content = content;
        this.endAt = endAt;
        this.step = step;
        this.files = files;
    }
}