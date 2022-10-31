package preCapstone.fuseable.model.note;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Step {

    TODO(0,"Todo"),
    DOING(1,"Doing"),
    VERIFY(2,"Verify"),
    DONE(3,"Done");

    private final int id;
    private final String step;
}
