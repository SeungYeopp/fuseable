package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.config.TotalUtil;
import preCapstone.fuseable.dto.file.FileDetailDto;
import preCapstone.fuseable.dto.note.*;
import preCapstone.fuseable.exception.ApiRequestException;
import preCapstone.fuseable.model.file.File;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.note.Step;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.file.FileRepository;
import preCapstone.fuseable.repository.note.NoteRepository;
import preCapstone.fuseable.repository.project.ProjectRepository;
import preCapstone.fuseable.repository.project.ProjectUserMappingRepository;
import preCapstone.fuseable.repository.user.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    private final ProjectUserMapping projectUserMapping;

    private final ProjectUserMappingRepository projectUserMappingRepository;

    private final ProjectRepository projectRepository;
    private final TotalUtil totalUtil;

    private final UserRepository userRepository;

    private final FileRepository fileRepository;


    @Transactional
    public NoteCreateDto createNote (Long projectId, NoteDetailDto noteDetail, User currentUser) {

        //projectID를 통하여 노트 리스트 전부를 받는다.
        List<Note> noteList = noteRepository.findByProjectId(projectId);

        //새로 create 된 note의 step 확인, date는 string to LocalDate, build를 위한 준비물
        Step step = Step.valueOf(noteDetail.getStep());
        LocalDate endAt = totalUtil.changeType(noteDetail.getEndAt());


        /*
        //해당 step에 가장 마지막 note을 찾으며 (next noteId == 0 인 노트), 없는 경우 Null을 준다
        Note lastNote = totalUtil.getTopNoteList(noteList)
                .stream()
                .filter(note -> note.getStep().equals(step))
                .findFirst().orElse(null);
         */



        //file의 url, name
        List<FileDetailDto> files = new ArrayList<>(noteDetail.getFiles());

        //해당 스텝의 노트가 하나라도 있는 경우.
        if(lastNote != null) {
            //프론트에서 받아낸 정보를 토대로 noteRepository에 저장, next id는 없으므로 비어있음
            Note noteSave = noteRepository.save(Note
                    .of(noteDetail,endAt, step, currentUser, projectId, lastNote.getPreviousId(),0L));

            //마지막 노트 다음 노트가 생겼으므로 이를 업데이트 해줌.
            lastNote.updatenextId(noteSave.getNoteId());

            //return을 위한 준비
            NoteCreateDto noteCreateDetail = NoteCreateDto.fromEntity(noteSave);

            //file관련 부분, 파일이름/파일id/유저/저장되는 노트
            files.stream()
                    .map(file -> new File(file.getFileName(), file.getFileUrl(), currentUser, noteSave))
                    .forEach(fileRepository::save);

            //파일 최종적인 갯수
            /*
            if (files.size() > totalUtil.getLimitOfFile/()) {
                throw new ApiRequestException(totalUtil.messageForLimitOfFile());
            }
             */

            noteCreateDetail.uploadFile(files);

            return noteCreateDetail;
        }

        //해당 스텝에 노트가 하나도 없는 경우
        else {
            //프론트에서 받아낸 정보를 토대로 noteRepository에 저장, previous, next id 둘다 없으므로 0L(Long)
            Note noteSave = noteRepository.save(Note
                    .of(noteDetail,endAt, step, currentUser, projectId, 0L,0L));

            //return을 위한 준비
            NoteCreateDto noteCreateDetail = NoteCreateDto.fromEntity(noteSave);

            //file관련 부분, 파일이름/파일id/유저/저장되는 노트
            files.stream()
                    .map(file -> new File(file.getFileName(), file.getFileUrl(), currentUser, noteSave))
                    .forEach(fileRepository::save);

            //파일 최종적인 갯수
            /*
            if (files.size() > totalUtil.getLimitOfFile/()) {
                throw new ApiRequestException(totalUtil.messageForLimitOfFile());
            }
             */

            noteCreateDetail.uploadFile(files);
            return noteCreateDetail;

        }
    }


    @Transactional
    public NoteDeleteDto deleteNote(Long noteId, User currentUser) {

        //현재의 id 및 전,후의 노트id를 가져옴, 없는 경우에는 NULL
        Note note = noteRepository.findById(noteId);
        Note previousNote = noteRepository.findById(note.getPreviousId()).orElse(null);
        Note nextNote = noteRepository.findById(note.getNextId()).orElse(null);


        //Case는 총 4가지로, 혼자이거나, 제일 앞이거나, 제일 뒤거나, 중간인 경우
        //혼자인 경우
        if((note.getPreviousId() == 0L) && (note.getNextId() == 0L )) {

        }
        //제일 앞인 경우
        else if((note.getPreviousId() == 0L) && (note.getNextId() != 0L )) {
            nextNote.updatepreviousId(0L);
        }

        //제일 뒤인 경우
        else if((note.getPreviousId() != 0L) && (note.getNextId() == 0L )) {
            previousNote.updatenextId(0L);
        }

        //중간인 경우, 현재 노트의 전,후의 id를 주어 전 후를 서로 이어줌
        else {
            nextNote.updatepreviousId(note.getPreviousId());
            previousNote.updatenextId(note.getNextId());
        }


        //Note에 연관된 파일 삭제
        fileRepository.deleteFileByNoteId(noteId);

        //noteId를 통해 자신 삭제
       noteRepository.deleteNoteById(noteId);

       //이외에 삭제할 Repository 추후 추가

        return NoteDeleteDto.builder()
                .noteId(noteId)
                .build();
    }


    //transaction 중 가장 엄격한 레벨의 어노테이션 , 동시성이 가장 높지만 다른 사람이 작업 중 건드릴 수 없음
    //칸반은 유저의 작업이라고 할만한 절차가 적으므로 괜찮음
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public NoteMoveDto moveNote(Long noteId, NoteMoveDetailDto noteMove, User currentUser){

        // 수정하려는 노트가 기존 id를 통해 검색했을 때 존재하지 않으면 Exception 반환.
        //해당 노트의 정보를 keep 하고 있음
        Note currentNote = noteRepository.findById(noteId).orElseThrow(() -> new ApiRequestException("수정하려는 노트가 존재하지 않음"));

        currentNote.updateMove(noteMove);

        //프론트의 배열은 0번부터, db의 id는 1부터 시작한다고 생각했음(0부터 가능하면 +1로)
        //예를들어 20개의 노트 배열이 있을때, 배열[2]번 노트가 배열[5]번으로 이동했다면
        //백엔드에선 3번 id가 빠지고 4,5,6이 한칸씩 앞으로 간 뒤, 3번이 6번 id자리를 채우는 형식
        for(Long noteNumNow= noteMove.getNoteId() + 2 ;noteNumNow < noteMove.getNewNoteId() + 2 ;noteNumNow++) {
            noteRepository.findByIdAndUpdateId(noteNumNow);
        }

        //위에서 업데이트 시킨 note 정보와 새로운 위치를 넘겨주어 저장케함
        noteRepository.saveNoteById(currentNote,noteMove.getNewNoteId());

        // Project로 전체 노트 리스트 가져오기
        List<Note> noteList = noteRepository.findByProject(currentNote.getProject());

        //전체 리스트를 재반환
        return NoteMoveDto.builder()
                .note(noteList)
                .build();
    }





    //front에서게 넘겨줄 데이터 옵션 중 하나, 상황봐야함
   /*
    @Transactional
    public NoteKanbanReadDto readKanban(Long projectId, User currentUser) {

        // Project로 전체 노트 리스트 가져오기
        List<Note> noteList = noteRepository.findByProject(projectUserMapping.getProject());

        //note의 리스트 생성, hash는 빌드업용
        List<Note> totalNoteList = totalUtil.getTopNoteList(noteList);
        Map<Long, Note> hashMap =totalUtil.getHashMap(noteList);

        //각 스텝 별 노트리스트를 담은 통합 리스트 연결리스트 순서에 맞게 재구성하여 가져온다
        List<List<NoteReadDto>> stepTotalList = totalUtil.getStepList(totalNoteList, hashMap);

        //각 스텝별 노트리스트
        List<NoteStepReadDto> noteStepRead = new ArrayList<>();

        // Step 별로 도는 용도
        List<Step> stepList = new ArrayList<>(Arrays.asList(Step.TODO, Step.DOING, Step.VERIFY, Step.DONE));

        // Step 별로 돌며 스텝 별 리스트에 resultList에 스텝 순서에 맞춰 들어간 정보를 가져온다
        for (Step step : stepList) {
            noteStepRead.add(NoteStepReadDto.of(step, stepTotalList.get(step.getId())));
        }

        return NoteKanbanReadDto.builder()
                .kanban(noteStepRead)
                .build();
    }
     */


}
