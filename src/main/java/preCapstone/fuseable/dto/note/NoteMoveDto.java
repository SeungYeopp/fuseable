package preCapstone.fuseable.dto.note;

import lombok.Getter;

@Getter
public class NoteMoveDto {

    //옮겨질 곳의 step, 전 후 id
    private String step;
    private Long newPreviousId;
    private Long newNextId;

}
