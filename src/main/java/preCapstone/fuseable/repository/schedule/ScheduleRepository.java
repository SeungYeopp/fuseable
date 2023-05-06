package preCapstone.fuseable.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.schedule.Schedule;
import preCapstone.fuseable.model.user.User;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryQuerydsl {

}
