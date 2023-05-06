package preCapstone.fuseable.model.note;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Step {

    TODO(1,"TODO"),
    PROGRESS(2,"PROGRESS"),
    VERIFY(3,"VERIFY"),
    DONE(4,"DONE");

    private final int id;
    private final String step;
}
