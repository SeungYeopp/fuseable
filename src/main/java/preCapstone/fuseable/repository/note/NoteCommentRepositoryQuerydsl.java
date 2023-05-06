package preCapstone.fuseable.repository.note;

import preCapstone.fuseable.model.note.NoteComment;

public interface NoteCommentRepositoryQuerydsl {

    NoteComment findByNoteId(Long noteId);
}
