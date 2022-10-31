package preCapstone.fuseable.model.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.user.User;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
public class Note {

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

    @Column(name = "DATE")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "STEP")
    private Step step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Column(name = "PREVIOUS")
    private Long previousId;

    @Column(name = "NEXT")
    private Long nextId;

    @Column(name = "WRITER_ID")
    private Long writerId;


    @Builder
    public Note(String title, String content, LocalDate date, Step step, User user, Project project, Long previousId, Long nextId) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.step = step;
        this.user = user;
        this.project = project;
        this.previousId = previousId;
        this.nextId = nextId;
        this.writerId = null;
    }


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
