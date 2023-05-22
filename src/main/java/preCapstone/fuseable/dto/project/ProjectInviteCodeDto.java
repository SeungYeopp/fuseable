package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectInviteCodeDto {

    private String inviteCode;

    @Builder
    public ProjectInviteCodeDto (String inviteCode) {
        this.inviteCode = inviteCode;
    }


}