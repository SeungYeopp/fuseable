package preCapstone.fuseable.dto.article;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname
) {

    public static ArticleResponse of(Long id, String title, String content, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.userDto().kakaoNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userDto().kakaoEmail();
        }

        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.createdAt(),
                dto.userDto().kakaoEmail(),
                nickname
        );
    }

}