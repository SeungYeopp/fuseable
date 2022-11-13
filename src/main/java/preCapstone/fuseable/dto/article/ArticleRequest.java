package preCapstone.fuseable.dto.article;

import java.util.Set;

public record ArticleRequest(
        String title,
        String content
) {

    public static ArticleRequest of(String title, String content) {
        return new ArticleRequest(title, content);
    }

    public ArticleDto toDto(UserDto userDto) {
        return ArticleDto.of(
                userDto,
                title,
                content
        );
    }

}