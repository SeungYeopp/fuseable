package preCapstone.fuseable.dto.article;

import org.springframework.security.core.userdetails.UserDetails;
import preCapstone.fuseable.model.user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record UserDto(
        Long kakaoId,
        String kakaoProfileImg,
        String kakaoNickname,
        String kakaoEmail,
        String userRole,
        Timestamp createTime
){

    public static UserDto of(Long kakaoId, String kakaoProfileImg, String kakaoNickname, String kakaoEmail, String userRole) {
        return new UserDto(kakaoId, kakaoProfileImg, kakaoNickname, kakaoEmail, userRole, null);
    }

    public static UserDto of(Long kakaoId, String kakaoProfileImg, String kakaoNickname, String kakaoEmail, String userRole, Timestamp createTime) {
        return new UserDto(kakaoId, kakaoProfileImg, kakaoNickname, kakaoEmail, userRole, createTime);
    }

    public static UserDto from(User user) {
        return new UserDto(
                user.getKakaoId(),
                user.getKakaoProfileImg(),
                user.getKakaoNickname(),
                user.getKakaoEmail(),
                user.getUserRole(),
                user.getCreateTime()
        );
    }

    public User toEntity() {
        return User.of(
                kakaoId,
                kakaoProfileImg,
                kakaoNickname,
                kakaoEmail,
                userRole
        );
    }

    public UserDto toDto() {
        return UserDto.of(
                kakaoId,
                kakaoProfileImg,
                kakaoNickname,
                kakaoEmail,
                userRole
        );
    }

}
