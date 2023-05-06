package preCapstone.fuseable.dto.unused;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoteCommentUpdateDto {

    private String comment;

    @Builder
    public NoteCommentUpdateDto(String comment) {
        this.comment = comment;
    }
}
