package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectInviteDto {
    private String EncodedId;

    @Builder
    public ProjectInviteDto(String EncodedId) {
        this.EncodedId = EncodedId;
    }
}
