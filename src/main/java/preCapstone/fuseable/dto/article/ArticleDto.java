package preCapstone.fuseable.dto.article;

import preCapstone.fuseable.model.article.Article;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleDto(
        Long id,
        Long userId,

        Long projectId,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        LocalDate startAt,

        Boolean bookmark
){

    public static ArticleDto of(Long userId, Long projectId, String title, String content, LocalDate startAt) {
        return new ArticleDto(null, userId, projectId, title, content, null, null, null, null, startAt,false);
    }

    public static ArticleDto of(Long id, Long userId, Long projectId, String title, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, LocalDate startAt) {
        return new ArticleDto(id, userId, projectId, title, content, createdAt, createdBy, modifiedAt, modifiedBy, startAt,false);
    }

//    public static ArticleDto from(Article article) {
//        return new ArticleDto(
//                article.getId(),
//                UserDto.from(article.getUser()),
//                article.getTitle(),
//                article.getContent(),
//                article.getCreatedAt(),
//                article.getCreatedBy(),
//                article.getModifiedAt(),
//                article.getModifiedBy()
//        );
//    }

    public Article toEntity(User user, Project project) {
        return Article.of(
                user,
                project,
                title,
                content,
                startAt,
                false
        );
    }
}