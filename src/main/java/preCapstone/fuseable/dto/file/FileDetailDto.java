package preCapstone.fuseable.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileDetailDto {
    private String fileName;
    private String fileUrl;

    @Builder
    public FileDetailDto(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

}
