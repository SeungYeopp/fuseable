package preCapstone.fuseable.model.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.dto.unused.NoteCommentUpdateDto;
import preCapstone.fuseable.model.user.User;

import javax.persistence.*;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
public class NoteComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="NOTE_ID")
    private Note note;

    @Column(name = "COMMENT", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "WRITER_ID")
    private Long writerId;

    @Builder
    public NoteComment(User user,Note note, String comment, Long writerId) {
        this.user = user;
        this.note = note;
        this.comment=comment;
        this.writerId=writerId;
    }

    public void updateComment(NoteCommentUpdateDto noteCommentUpdateDto) {
        this.comment= noteCommentUpdateDto.getComment();
    }

}
