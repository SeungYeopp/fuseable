package preCapstone.fuseable.model.project;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.model.User;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class UserProjectMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    /*@Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "ROLE")
    private UserProjectRole role;*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PROJECT_ID")
    private Project project;

    @Builder
    public UserProjectMapping( User user, Project project) {
        this.user = user;
        this.project = project;
       // this.role = userProjectRole;
    }
}
