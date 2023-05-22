package preCapstone.fuseable.model.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.user.User;

import javax.persistence.*;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @Column(name = "COMMENT", columnDefinition = "TEXT")
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="NOTE_ID")
    private Note note;

    @Builder
    public Comment(String comment, User user, Note note) {
        this.comment = comment;
        this.user = user;
        this.note = note;
    }

}
