package preCapstone.fuseable.repository.article;

import preCapstone.fuseable.model.article.Article;

import java.util.List;

public interface ArticleRepositoryQuerydsl {
    List<Article> findAllByProjectId (Long projectId);
}
