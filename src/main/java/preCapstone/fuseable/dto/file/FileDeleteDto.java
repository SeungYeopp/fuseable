package preCapstone.fuseable.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileDeleteDto {
    private Long fileId;

    @Builder
    public FileDeleteDto(Long fileId) {
        this.fileId = fileId;
    }

}