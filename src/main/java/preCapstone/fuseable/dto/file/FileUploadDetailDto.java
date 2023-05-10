package preCapstone.fuseable.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileUploadDetailDto {
    private String fileName;
    private String fileRandomName;
    private String fileUrl;

    @Builder
    public FileUploadDetailDto(String fileName, String fileRandomName, String fileUrl) {
        this.fileName = fileName;
        this.fileRandomName = fileRandomName;
        this.fileUrl = fileUrl;
    }

}
