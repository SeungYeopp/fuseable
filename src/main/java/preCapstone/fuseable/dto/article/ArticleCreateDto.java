package preCapstone.fuseable.dto.article;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.article.Article;

@Getter
public class ArticleCreateDto {
    private Long articleId;
    private String title;
    private String content;

    @Builder
    public ArticleCreateDto(Long articleId, String title, String content) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
    }


    public static ArticleCreateDto fromEntity (Article article) {
        return ArticleCreateDto.builder()
                .title(article.getTitle())
                .articleId(article.getId())
                .content(article.getContent())
                .build();
    }
}