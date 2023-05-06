package preCapstone.fuseable.dto.article;

import java.time.LocalDate;
import java.util.Set;

public record ArticleRequest(
        String title,
        String content,
        LocalDate startAt
) {

    public static ArticleRequest of(String title, String content, LocalDate startAt) {
        return new ArticleRequest(title, content, startAt);
    }

    public ArticleDto toDto(Long userId, Long projectId) {
        return ArticleDto.of(
                userId,
                projectId,
                title,
                content,
                startAt
        );
    }

}