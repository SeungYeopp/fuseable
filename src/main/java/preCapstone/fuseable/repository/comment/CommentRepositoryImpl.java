package preCapstone.fuseable.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class CommentRepositoryImpl implements CommentRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    public CommentRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory=new JPAQueryFactory(em);
    }
}
