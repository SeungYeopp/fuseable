package preCapstone.fuseable.dto.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleUpdateDetailDto {

    private String checkBox;

    @Builder
    public ScheduleUpdateDetailDto(String checkBox) {
        this.checkBox = checkBox;
    }
}
