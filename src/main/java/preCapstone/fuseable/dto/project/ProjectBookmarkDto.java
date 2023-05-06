package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectBookmarkDto {

    private Boolean bookmark;

    @Builder
    public ProjectBookmarkDto(Boolean bookmark) {
        this.bookmark = bookmark;
    }



}
