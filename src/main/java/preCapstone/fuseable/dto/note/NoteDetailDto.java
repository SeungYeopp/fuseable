package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoteDetailDto {
    private String title;
    private String content;
    private String endAt;
    private String step;

    @Builder
    public NoteDetailDto(String title, String content, String endAt, String step) {
        this.title = title;
        this.content = content;
        this.endAt = endAt;
        this.step = step;
    }
}