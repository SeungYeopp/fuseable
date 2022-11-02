package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectCrewDto {

    //crew의 정보를 담는곳
    //프로필 사진이나 닉네임 등도 고려중.
    private Long userId;

    @Builder
    public ProjectCrewDto(Long userId) {
        this.userId = userId;
    }
}
