package preCapstone.fuseable.dto.schedule;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleUpdateDto {

    private String checkBox;

    @Builder
    public ScheduleUpdateDto(String checkBox) {
        this.checkBox = checkBox;
    }

}
