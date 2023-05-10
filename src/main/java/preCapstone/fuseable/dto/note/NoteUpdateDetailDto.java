package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import preCapstone.fuseable.dto.file.FileUploadDetailDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoteUpdateDetailDto {

    //note의 관한 정보로 projectid/제목/내용/마감기한/스텝/파일

    private String title;
    private String content;
    private String endAt;

    private List<FileUploadDetailDto> files;

    @Builder
    public NoteUpdateDetailDto(String title, String content, String endAt, List<FileUploadDetailDto> files) {
        this.title = title;
        this.content = content;
        this.endAt = endAt;
        this.files = files;
    }
}