package preCapstone.fuseable.dto.note;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Step;

@Getter
public class NoteCreateDetailDto {

    //note 생성이 필요한 정보들
    //note Id, 제목, 내용, 끝나는 날짜, 스텝
    private Long arrayId;
    private String step;

    //endAt과 step은 각각 localdate, step 이었기때문에 string으로 바꿔서 넣어줌
    @Builder
    public NoteCreateDetailDto(Long arrayId, Step step) {
        this.arrayId = arrayId;
        this.step = step.toString();
    }
}
