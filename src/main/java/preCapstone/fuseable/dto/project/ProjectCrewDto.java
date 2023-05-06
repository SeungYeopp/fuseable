package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProjectCrewDto {

    //crew의 정보를 담는곳
    //프로필 사진이나 닉네임 등도 고려중.
    private Long userId;

    private String userName;
    private String userPicture;

    @Builder
    public ProjectCrewDto(Long userId, String userName, String userPicture) {

        this.userId = userId;
        this.userName=userName;
        this.userPicture=userPicture;
    }


}
