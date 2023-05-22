package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectCreateInviteDto {

private String inviteCode;

        @Builder
        public ProjectCreateInviteDto(String inviteCode) {
            this.inviteCode = inviteCode;
        }

}
