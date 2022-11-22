package preCapstone.fuseable.repository.note;

import preCapstone.fuseable.dto.note.NoteResponseDto;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.user.User;

import java.util.List;
import java.util.Optional;

public interface NoteRepositoryQuerydsl {

    List<Note> findAllByProjectId(long projectId);

    Note findByArrayIdAndProjectId(Long arrayId, Long projectId);

   // Optional<NoteResponseDto> findByNoteId(Long NoteId);

    void deleteNoteById(Long noteId);

    void deleteNoteByProjectId(Long projectId);
    List<Note> findByProjectIdAndUserId (Long projectId,  Long userId);
    void updateArrayIdByProjectId(Long projectId,Long arrayId);
    void updateMoveArrayId(Long projectId,Long arrayId, Long newArrayId);









}
