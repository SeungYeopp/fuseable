package preCapstone.fuseable.model.project;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class Project{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "TITLE")
    private String title;




    @Builder
    public Project(String title){
        this.title = title;
    }


    //ProjectRequestDto를 service에서 직접 지목해주어야하는 형태의 코드
    public static Project toEntity(String title) {
        return Project.builder()
                .title(title)
                .build();
    }

    public void updateTitle (String title)
    {
        this.title = title;
    }



}
