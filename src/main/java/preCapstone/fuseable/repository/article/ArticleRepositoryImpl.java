package preCapstone.fuseable.repository.article;

import com.querydsl.jpa.impl.JPAQueryFactory;
import preCapstone.fuseable.model.article.Article;
import preCapstone.fuseable.model.article.QArticle;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;
    public ArticleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    QArticle article = new QArticle("article");

    @Override
    public List<Article> findAllByProjectId(Long projectId) {
        return queryFactory
                .selectFrom(article)
                .where(article.project.projectId.eq(projectId))
                .fetch();
    }

    @Override
    public List<Article> findBookmarkByProjectIdAndDate (Long projectId, LocalDate currentDay) {
        return queryFactory
                .selectFrom(article)
                .where(
                        article.project.projectId.eq(projectId)
                                .and(article.startAt.between(currentDay, currentDay.plusDays(3)))
                )
                .fetch();
    }

}
