package preCapstone.fuseable.repository.project;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import preCapstone.fuseable.dto.project.ProjectDetailDto;
import preCapstone.fuseable.model.note.QNote;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.QProject;

import javax.persistence.EntityManager;
import java.util.List;

public class ProjectRepositoryImpl implements ProjectRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;


    public ProjectRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QProject project = new QProject("p");
    QNote note = new QNote("note");

    @Override
    public List<ProjectDetailDto> findByProjectId(List<Long> projectIdList) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ProjectDetailDto.class,
                                project.projectId, project.title, note.modifiedDate.max()))
                .from(project)
                .leftJoin(note)
                .on(note.project.eq(project))
                .where(project.projectId.in(projectIdList))
                .groupBy(project.projectId)
                .orderBy(note.modifiedDate.max().desc())
                .fetch();
    }

    public Project findByOneProjectId(Long projectId){
        return em.find(Project.class, projectId);
    }
}
