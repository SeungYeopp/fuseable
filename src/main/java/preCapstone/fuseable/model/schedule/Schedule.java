package preCapstone.fuseable.model.schedule;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_Id")
    private Long scheduleId;

    @Column(name = "USER_ID")
    private Long  userCode;

    @Column(name = "time_check")
    private Boolean timeCheck;

    @Column(name = "day")
    private Long day;

    @Column(name = "time")
    private Long time;

    @Builder
    public Schedule(Long scheduleId, Long userCode, Boolean timeCheck, Long day, Long time) {
        this.scheduleId = scheduleId;
        this.userCode = userCode;
        this.timeCheck = timeCheck;
        this.day= day;
        this.time = time;
    }

    //public void updateTimeCheck()


}
