package preCapstone.fuseable.model.schedule;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import preCapstone.fuseable.dto.note.NoteUpdateDetailDto;
import preCapstone.fuseable.dto.schedule.ScheduleUpdateDetailDto;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.user.User;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Schedule {

    //스케쥴ID, user(Id용), checkbox, 요일, 시간
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_Id")
    private Long scheduleId;

    //각 유저의
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "USER_ID")
    private User user;

    //010001 이런식으로 7day * 1.5시간 * 10 (9~24시)
    @Column(name = "checkBox")
    private String checkBox;


    @Builder
    public Schedule(User user,String checkBox) {
        this.user = user;
        this.checkBox = checkBox;
    }


    public void updateCheckBox (ScheduleUpdateDetailDto updateDetail) {
        this.checkBox= updateDetail.getCheckBox();
    }


}
