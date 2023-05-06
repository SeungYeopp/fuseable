package preCapstone.fuseable.repository.comment;

import preCapstone.fuseable.model.note.NoteComment;

public interface CommentRepositoryQuerydsl {

    NoteComment findByNoteId(Long noteId);
}
