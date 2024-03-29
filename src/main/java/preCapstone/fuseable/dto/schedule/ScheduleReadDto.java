package preCapstone.fuseable.dto.schedule;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleReadDto {

    private Long scheduleId;

    private String checkBox;

    @Builder
    public ScheduleReadDto(Long scheduleId, String checkBox) {
        this.scheduleId = scheduleId;
        this.checkBox = checkBox;
    }
}
