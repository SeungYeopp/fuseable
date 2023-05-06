package preCapstone.fuseable.model.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.dto.note.NoteCommentUpdateDto;

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

    @Column(name ="Note_ID")
    private Long noteId;

    @Column(name = "COMMENT", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "WRITER_ID")
    private Long writerId;

    @Builder
    public NoteComment(Long noteId, String comment, Long writerId) {

        this.noteId=noteId;
        this.comment=comment;
        this.writerId=writerId;
    }

    public void updateComment(NoteCommentUpdateDto noteCommentUpdateDto) {
        this.comment= noteCommentUpdateDto.getComment();
    }

}
