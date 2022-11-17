package preCapstone.fuseable.config;

import org.springframework.stereotype.Component;
import preCapstone.fuseable.dto.note.NoteReadDto;
import preCapstone.fuseable.model.note.Note;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TotalUtil {

   // private final int LIMIT_OF_FILES = 5;

    public List<Note> getTopNoteList(List<Note> NoteList) {
        return NoteList
                .stream()
                .filter(note -> note.getNextId().toString().equals("0"))
                .collect(Collectors.toList());
    }


    //문자열(시간)을 Localdate 형식으로 바꿈, 그래야 createnote 서비스에서 return 값과 맞아떨어짐
    public LocalDate changeType(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date;
    }

    //getStepList를 위한 빌드업용
    //noteList를 통하여 hash 만들어줌
    public Map<Long, Note> getHashMap(List<Note> noteList) {
        return noteList
                .stream()
                .collect(Collectors.toMap(Note::getNoteId, note -> note));
    }

    //2차원 배열을 위한 부분
    public List<List<NoteReadDto>> getStepList(List<Note> totalNoteList, Map<Long, Note> hashMap) {

        //step로 나누어 NoteRead를 담는 형식, 2차원 배열
        List<List<NoteReadDto>> stepTotalList = new ArrayList<>();

        // 각 step별 note 생성
       List<NoteReadDto> Todo = new ArrayList<>();
        List<NoteReadDto> Doing= new ArrayList<>();
        List<NoteReadDto> Verify = new ArrayList<>();
        List<NoteReadDto> Done = new ArrayList<>();


        //for로 처음부터 다음 id가 null일때까지 반복을 통해 각 step별로 note를 넣어주는 형식
        for (Note topNote : totalNoteList) {
            Note pointer = topNote;
            while (pointer != null) {
                Note target = hashMap.get(pointer.getNoteId());
                switch (pointer.getStep()) {
                    case TODO:
                        Todo.add(NoteReadDto.of(target));
                        break;
                    case DOING:
                        Doing.add(NoteReadDto.of(target));
                        break;
                    case VERIFY:
                        Verify.add(NoteReadDto.of(target));
                        break;
                    case DONE:
                        Done.add(NoteReadDto.of(target));
                        break;
                }
                pointer = hashMap.get(target.getNextId());
            }
        }

        //각 step별로 채워진 note를 stepList라는 리스트에 다 넣어줌. == 2차원 배열
        stepTotalList.add(Todo);
        stepTotalList.add(Doing);
        stepTotalList.add(Verify);
        stepTotalList.add(Done);

        return stepTotalList;
    }



    /*
    public int getLimitOfFile() {
        return LIMIT_OF_FILES;
    }

    public String messageForLimitOfFile() {
        return "파일을 " + getLimitOfFile() + "개 이상 생성할수 없습니다";
    }
     */
}
