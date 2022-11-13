package preCapstone.fuseable.model.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.model.Timestamped;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.user.User;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
public class Note extends Timestamped {

    //노트에 필요한 항목
    //노트Id,노트Title,내용,기간, 스텝, 유저, 해당노트 프로젝트 id, 노트 앞, 노트 뒤, 작성자

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    //현 프로젝트
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    //Note의 뒷 ID
    @Column(name = "PREVIOUS")
    private Long previousId;

    //Note의 앞 ID, 순서용
    @Column(name = "NEXT")
    private Long nextId;

    //유저 닉네임이나 유저 ID로 해야할듯
    @Column(name = "WRITER_ID")
    private Long writerId;


    @Builder

    public Note(Long noteId, String title, String content,  LocalDate startAt, LocalDate endAt, Step step, User user, Project project, Long previousId, Long nextId, Long writerId) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.step = step;
        this.user = user;
        this.project = project;
        this.previousId = previousId;
        this.nextId = nextId;
        this.writerId = null;
    }
//    @Builder
//    public Note(String title, String content, LocalDate date, Step step, User user, Project project, Long previousId, Long nextId) {
//        this.title = title;
//        this.content = content;
//        this.date = date;
//        this.step = step;
//        this.user = user;
//        this.project = project;
//        this.previousId = previousId;
//        this.nextId = nextId;
//        this.writerId = null;
//    }


//    public static Note of(NoteCreateRequestDto noteCreateRequestDto, LocalDate deadline, Step step, User user, Project project, Long previousId, Long nextId) {
//        return Note.builder()
//                .title(noteCreateRequestDto.getTitle())
//                .content(noteCreateRequestDto.getContent())
//                .deadline(deadline)
//                .step(step)
//                .user(user)
//                .project(project)
//                .previousId(previousId)
//                .nextId(nextId)
//                .build();
//    }


}
