package preCapstone.fuseable.model.article;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import preCapstone.fuseable.model.user.User;

import javax.persistence.*;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "USER_ID")
    @ManyToOne(optional = false)
    private User user; // 유저 정보 (ID)

    @Setter @Column(nullable = false)
    private String title; // 제목
    @Setter @Column(nullable = false, length = 10000)
    private String content; // 본문

    protected Article() {}

    private Article(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public static Article of(User user, String title, String content) {
        return new Article(user, title, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }


}
