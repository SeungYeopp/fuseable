package preCapstone.fuseable.repository.article;

import preCapstone.fuseable.model.article.Article;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepositoryQuerydsl {
    List<Article> findAllByProjectId (Long projectId);

    List<Article> findBookmarkByProjectIdAndDate (Long projectId, LocalDate currentDay);
}
