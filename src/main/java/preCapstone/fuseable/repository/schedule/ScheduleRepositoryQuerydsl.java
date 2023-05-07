package preCapstone.fuseable.repository.schedule;

import preCapstone.fuseable.dto.schedule.ScheduleUpdateDetailDto;
import preCapstone.fuseable.model.schedule.Schedule;

import java.util.Optional;

public interface ScheduleRepositoryQuerydsl {

    Optional<Schedule> findByUserId(Long userId);

    void updateCheckBoxById(Long scheduleId, ScheduleUpdateDetailDto scheduleDetail);

    String findCheckBoxByUserId(Long userId);


}
