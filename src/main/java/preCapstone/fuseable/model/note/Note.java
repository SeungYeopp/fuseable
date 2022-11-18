package preCapstone.fuseable.model.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.dto.note.NoteDetailDto;
import preCapstone.fuseable.dto.note.NoteMoveDetailDto;
import preCapstone.fuseable.model.Timestamped;
import preCapstone.fuseable.model.user.User;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
public class Note extends Timestamped {

    //노트에 필요한 항목
    //노트Id,노트Title,내용,기간, 스텝, 유저, 해당노트 프로젝트 id
    //노트 앞, 뒤는 linked list인 경우 필요하지만 지금은 필요없음
    //작성자는 user 가져다 쓰면될듯

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Long noteId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "START_DATE")
    private LocalDate startAt;

    @Column(name = "END_DATE")
    private LocalDate endAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STEP")
    private Step step;


    /* linked list를 사용하는 경우에만 필요함
    //Note의 전 ID, 순서용
    @Column(name = "PREVIOUS")
    private Long previousId;

    //Note의 다음 ID, 순서용
    @Column(name = "NEXT")
    private Long nextId;
     */


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    //현 프로젝트
    @JoinColumn(name = "PROJECT_ID")
    private Long projectId;


    //유저 닉네임이나 유저 ID로 해야할듯
    @Column(name = "WRITER_ID")
    private Long writerId;


    @Builder

    public Note(Long noteId, String title, String content,  LocalDate startAt, LocalDate endAt, Step step, User user, Long projectId, Long writerId) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.user = user;
        this.projectId = projectId;
        this.writerId = null;
        this.step = step;
    }

    /* linked list를 사용하는 경우에만 필요함
    public void updatenextId(Long nextId) {
        this.nextId = nextId;
    }

    public void updatepreviousId(Long previousId) {
        this.previousId = nextId;
    }
     */

    public void updateMove(NoteMoveDetailDto noteMove) {
        this.step = noteMove.getNewStep();
    }

    public static Note of(NoteDetailDto noteDetail,LocalDate endAt, Step step, User user, Long projectId) {
        return Note.builder()
            .title(noteDetail.getTitle())
            .content(noteDetail.getContent())
            .endAt(endAt)
            .step(step)
            .user(user)
            .projectId(projectId)
            .build();
    }


}
