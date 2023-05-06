package preCapstone.fuseable.repository.schedule;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class ScheduleRepositoryImpl implements ScheduleRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

   public ScheduleRepositoryImpl(EntityManager em) {
       this.em = em;
       this.queryFactory=new JPAQueryFactory(em);
   }
}
