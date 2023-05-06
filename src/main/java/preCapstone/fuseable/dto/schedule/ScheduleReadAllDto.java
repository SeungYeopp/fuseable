package preCapstone.fuseable.dto.schedule;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleReadAllDto {

    private String checkBox;


    @Builder
    public ScheduleReadAllDto(String checkBox) {
        this.checkBox = checkBox;
    }
}
