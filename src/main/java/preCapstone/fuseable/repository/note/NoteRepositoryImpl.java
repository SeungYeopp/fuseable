package preCapstone.fuseable.repository.note;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import preCapstone.fuseable.dto.note.NoteResponseDto;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.note.QNote;
import preCapstone.fuseable.model.project.QProject;
import preCapstone.fuseable.model.user.QUser;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class NoteRepositoryImpl implements NoteRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    QNote note = new QNote("note");
    QProject project = new QProject("project");

    QUser user = new QUser("user");


    public NoteRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteNoteByProjectId(Long projectId) {
        queryFactory
                .delete(note)
                .where(note.project.projectId.eq(projectId))
                .execute();
    }

    @Override
    public void deleteNoteById(Long noteId) {
        queryFactory
                .delete(note)
                .where(note.noteId.eq(noteId))
                .execute();
    }


    @Override
    public List<Note> findAllByProjectId(long projectId) {
        return queryFactory
                .selectFrom(note)
                .where(note.project.projectId.eq(projectId))
                .fetch();
    }

    @Override
    public Note findByArrayIdAndProjectId(Long arrayId, Long projectId) {

        return queryFactory
                .selectFrom(note)
                .where(note.project.projectId.eq(projectId).and(note.arrayId.eq(arrayId)))
                .fetchOne();
    }

    @Override
    public List<Note> findByProjectIdAndUserId(Long projectId, Long userId) {

        return queryFactory
                .select(note)
                .from(note)
                .where(note.project.projectId.eq(projectId).and(note.user.userCode.eq(userId)))
                .fetch();
    }

    @Override
    public void updateArrayIdByProjectId(Long projectId, Long arrayId) {
        queryFactory
                .update(note)
                .set(note.arrayId, note.arrayId.subtract(1))
                .where(note.project.projectId.eq(projectId).and(note.arrayId.gt(arrayId)))
                .execute();
    }
    @Override
    public void updateMoveFrontArrayId(Long projectId, Long arrayId, Long newArrayId) {
        queryFactory
                .update(note)
                .set(note.arrayId, note.arrayId.add(1))
                .where(note.project.projectId.eq(projectId).and(note.arrayId.lt(arrayId)).and(note.arrayId.goe(newArrayId))).execute();
    }
    @Override
    public void updateMoveBackArrayId(Long projectId, Long arrayId, Long newArrayId) {
        queryFactory
                .update(note)
                .set(note.arrayId, note.arrayId.subtract(1))
                .where(note.project.projectId.eq(projectId).and(note.arrayId.gt(arrayId)).and(note.arrayId.loe(newArrayId))).execute();
    }

    public List<Note> alarmNote(Long projectId) {
        LocalDate today = LocalDate.now();
        LocalDate fourDaysLater = today.plusDays(4);

        return queryFactory.selectFrom(note)
                .where(
                        note.project.projectId.eq(projectId)
                                .and(note.endAt.after(today.minusDays(1)))
                                .and(note.endAt.before(fourDaysLater)))
                .fetch();
    }

    //    public Optional<NoteResponseDto> findByNoteId(Long noteId) {
//        return Optional.ofNullable(
//                queryFactory
//                        .select(
//                                Projections.constructor
//                                        (NoteResponseDto.class,
//                                                note.noteId,
//                                                note.arrayId,
//                                                note.title,
//                                                note.content,
//                                                note.endAt,
//                                                note.step,
//                                                project.projectId,
//                                                project.title,
//                                                user.kakaoNickname
//                                        ))
//                        .from(note)
//                        .join(note.project, project)
//                        .on(note.noteId.eq(noteId))
//                        .join(note.user, user)
//                        .fetchOne());
//    }
}
