package preCapstone.fuseable.model.project;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.model.Timestamped;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class Project extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "TITLE")
    private String title;

    @Column(columnDefinition = "TEXT", name = "DETAIL")
    private String detail;

    @Builder Project(String title, String detail){
        this.title = title;
        this.detail = detail;
    }


    //ProjectRequestDto를 service에서 직접 지목해주어야하는 형태의 코드
    public static Project toEntity(ProjectRequestDto requestDto) {
        return Project.builder()
                .title(requestDto.getTitle())
                .detail(requestDto.getDetail())
                .build();
    }

    public void update(ProjectRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.detail = requestDto.getDetail();
    }




}
