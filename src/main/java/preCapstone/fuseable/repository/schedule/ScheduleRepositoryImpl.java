package preCapstone.fuseable.repository.schedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import preCapstone.fuseable.dto.schedule.ScheduleUpdateDetailDto;
import preCapstone.fuseable.model.schedule.Schedule;

import javax.persistence.EntityManager;
import java.util.Optional;

public class ScheduleRepositoryImpl implements ScheduleRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

   public ScheduleRepositoryImpl(EntityManager em) {
       this.em = em;
       this.queryFactory=new JPAQueryFactory(em);
   }

   //QSchedule schedule = new QSchedule()  <- 이 부분 어떻게

    @Override
    public Optional<Schedule> findByUserId(Long userId) {
        return Optional.ofNullable(queryFactory
                .select(schedule)
                .from(schedule)
                .where(schedule.user.userCode.eq(userId),)
                .fetchFirst());
    }

    @Override
    public Optional<Schedule> updateCheckBoxById(Long scheduleId, ScheduleUpdateDetailDto scheduleUpdateDetail) {
        queryFactory
                .update(schedule)
                .set(schedule.checkBox, scheduleUpdateDetail)
                .where(schedule.scheduleId.eq(scheduleId))
                .execute();
    }

    @Override //이거 하나만...
    public String findCheckBoxByUserId(Long userId) {
        return
    }
}
