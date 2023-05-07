package preCapstone.fuseable.repository.schedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import preCapstone.fuseable.dto.schedule.ScheduleUpdateDetailDto;
import preCapstone.fuseable.model.schedule.QSchedule;
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

   QSchedule schedule = new QSchedule("s");

    @Override
    public Optional<Schedule> findByUserId(Long userId) {
        return Optional.ofNullable(queryFactory
                .select(schedule)
                .from(schedule)
                .where(schedule.user.userCode.eq(userId))
                .fetchFirst());
    }

    @Override
    public void updateCheckBoxById(Long scheduleId, ScheduleUpdateDetailDto scheduleUpdateDetail) {
        queryFactory
                .update(schedule)
                .set(schedule.checkBox, scheduleUpdateDetail.getCheckBox())
                .where(schedule.scheduleId.eq(scheduleId))
                .execute();
    }

    @Override
    public String findCheckBoxByUserId(Long userId) {
        return queryFactory
                .select(schedule.checkBox)
                .from(schedule)
                .where(schedule.user.userCode.eq(userId))
                .fetchOne();
    }
}
