package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.dto.comment.CommentListDto;
import preCapstone.fuseable.model.comment.Comment;
import preCapstone.fuseable.model.file.File;
import preCapstone.fuseable.model.note.Note;

import java.time.LocalDate;
import java.util.List;

@Getter
public class NoteReadDto {
    private Long noteId;
    private String title;
    private String content;
    private String endAt;
    private String startAt;
    private List<Comment> comments;
    private List<File> files;


    @Builder
    public NoteReadDto(Long noteId, String title, String content, LocalDate endAt, LocalDate startAt, List<Comment> comments, List<File> files) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.endAt = endAt.toString();
        this.startAt = startAt.toString();
        this.comments = comments;
        this.files = files;
    }

}