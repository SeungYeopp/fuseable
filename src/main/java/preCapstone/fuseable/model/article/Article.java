package preCapstone.fuseable.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@ToString(callSuper = true)
//@Table(indexes = {
//        @Index(columnList = "title"),
//        @Index(columnList = "createdAt"),
//        @Index(columnList = "createdBy")
//})
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "USER_ID")
    @ManyToOne(optional = false)
    private User user; // 유저 정보 (ID)

    @Setter
    @JoinColumn(name = "PROJECT_ID")
    @ManyToOne(optional = false)
    @JsonIgnore
    private Project project;
    @Setter @Column(nullable = false)
    private String title; // 제목
    @Setter @Column(nullable = false, length = 10000)
    private String content; // 본문

    @Column(name = "CREATE_DATE")
    private LocalDate startAt;

    protected Article() {}

    private Article(User user, Project project, String title, String content, LocalDate startAt) {
        this.user = user;
        this.project = project;
        this.title = title;
        this.content = content;
        this.startAt = startAt;
    }

    public static Article of(User user, Project project, String title, String content, LocalDate startAt) {
        return new Article(user, project, title, content, startAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }


}
