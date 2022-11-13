package preCapstone.fuseable.dto.article;

import preCapstone.fuseable.model.article.Article;
import preCapstone.fuseable.model.user.User;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleDto(
        Long id,
        UserDto userDto,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
){

    public static ArticleDto of(UserDto userDto, String title, String content) {
        return new ArticleDto(null, userDto, title, content, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserDto userDto, String title, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userDto, title, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article article) {
        return new ArticleDto(
                article.getId(),
                UserDto.from(article.getUser()),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getCreatedBy(),
                article.getModifiedAt(),
                article.getModifiedBy()
        );
    }

    public Article toEntity(User user) {
        return Article.of(
                user,
                title,
                content
        );
    }
}
