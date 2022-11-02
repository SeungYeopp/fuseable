package preCapstone.fuseable.repository.project;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.project.QProjectUserMapping;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ProjectUserMappingRepositoryImpl implements ProjectUserMappingRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    public ProjectUserMappingRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    QProjectUserMapping projectUserMapping = new QProjectUserMapping("q");

    @Override
    public List<ProjectUserMapping> findByUserId(Long userId) {
        return queryFactory
                .select(projectUserMapping)
                .from(projectUserMapping)
                .where(projectUserMapping.user.userCode.eq(userId))
                .fetch();
    }

    @Override
    public Optional<ProjectUserMapping> findByUserIdAndProjectId(Long userId, Long projectId) {
        return Optional.ofNullable(queryFactory
                .select(projectUserMapping)
                .from(projectUserMapping)
                .where(projectUserMapping.user.userCode.eq(userId), projectUserMapping.project.projectId.eq(projectId))
                .fetchFirst());
    }

    @Override
    public void deleteByProjectId(Long projectId) {
        queryFactory
                .delete(projectUserMapping)
                .where(projectUserMapping.project.projectId.eq(projectId))
                .execute();
    }


    
}
