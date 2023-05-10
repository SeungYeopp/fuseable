package preCapstone.fuseable.repository.file;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import preCapstone.fuseable.model.file.File;

import javax.persistence.EntityManager;

import java.util.List;

import static preCapstone.fuseable.model.file.QFile.file;
import static preCapstone.fuseable.model.note.QNote.note;


public class FileRepositoryImpl implements FileRepositoryQuerydsl{
    private final JPAQueryFactory queryFactory;

    public FileRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);

    }

    @Override
    public void deleteFileByNoteId(Long noteId) {
        queryFactory
                .delete(file)
                .where(file.note.noteId.eq(noteId))
                .execute();
    }

    @Override
    public void deleteFileByProjectId(Long projectId) {

        queryFactory
                .delete(file)
                .where(file.note.noteId.in(
                        JPAExpressions
                                .select(note.noteId)
                                .from(note)
                                .where(note.project.projectId.eq(projectId))
                ))
                .execute();
    }

    @Override
    public File findByFileId(Long fileId) {

        return queryFactory
                .select(file)
                .from(file)
                .where(file.fileId.eq(fileId))
                .fetchOne();
    }


}
