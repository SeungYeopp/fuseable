package preCapstone.fuseable.dto.schedule;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleCrewDto {

    private Long userId;

    @Builder
    public ScheduleCrewDto(Long userId) {
        this.userId = userId;

    }
}