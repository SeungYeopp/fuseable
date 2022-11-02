package preCapstone.fuseable.model.calendar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import preCapstone.fuseable.model.Timestamped;
import preCapstone.fuseable.model.user.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Notification extends Timestamped {


    private Long id;
    private LocalDateTime notifyAt;
    private String title;
    private User Writer;
}
