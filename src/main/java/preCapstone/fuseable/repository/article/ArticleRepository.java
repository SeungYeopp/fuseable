package preCapstone.fuseable.repository.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import preCapstone.fuseable.model.article.Article;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long>, QuerydslPredicateExecutor<Article> {

    void deleteByIdAndUser(Long articleId, String userId);
}
