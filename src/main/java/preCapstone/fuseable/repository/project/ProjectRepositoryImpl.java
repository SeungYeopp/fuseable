package preCapstone.fuseable.repository.project;

import com.querydsl.jpa.impl.JPAQueryFactory;
import preCapstone.fuseable.model.project.QProject;

import javax.persistence.EntityManager;

public class ProjectRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    QProject project = new QProject("p");


}
