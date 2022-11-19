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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    private final ProjectUserMapping projectUserMapping;

    private final ProjectUserMappingRepository projectUserMappingRepository;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;
    private final TotalUtil totalUtil;
    private final FileRepository fileRepository;
    private final EntityManager em;


    @Transactional
    public NoteCreateDto createNote (Long projectId, NoteDetailDto noteDetail, User currentUser) {

        //projectID를 통하여 해당 프로젝트와 관련된 노트리스트를 전부 받는다.
        List<Note> noteList = noteRepository.findByProjectId(projectId);

        //새로 create 된 note의 step(String to Step)확인, date는 String to LocalDate, build를 위한 준비물
        Step step = Step.valueOf(noteDetail.getStep());
        LocalDate endAt = totalUtil.changeType(noteDetail.getEndAt());

        //가장 마지막 note을 찾으며 (next noteId == 0 인 노트), 없는 경우 Null을 준다
        //note의 앞선 id가 있는지 알아야하므로 진행함

        Note lastNote = totalUtil.getLastNote(noteList)
                .stream()
                .findFirst().orElse(null);

        //file의 url, name
        List<FileDetailDto> files = new ArrayList<>(noteDetail.getFiles());


        //노트가 하나라도 있는 경우.
        if (lastNote != null)
        {

            //프론트에서 받아낸 정보를 토대로 noteRepository에 저장, next id는 없으므로 비어있음
            /*
            들어가야할 목록
            title/content/endAt/step/user(글쓴이)/projectId/previousId(lastnoteid)/nextId(0)/배열Id
            */
            Note noteSave = noteRepository.save(Note
                    .of(noteDetail, endAt, step, currentUser, projectId, lastNote.getNoteId(), 0L, lastNote.getArrayId() + 1));

            //마지막 노트 다음 노트가 생겼으므로 이를 업데이트 해줌.
            lastNote.updatenextId(noteSave.getNoteId());

            //return을 위한 준비
            NoteCreateDto noteCreateDetail = NoteCreateDto.fromEntity(noteSave);

            //file관련 부분, 파일이름/파일id/유저/저장되는 노트
            files.stream()
                    .map(file -> new File(file.getFileName(), file.getFileUrl(), currentUser, noteSave))
                    .forEach(fileRepository::save);

            //file size가 5mb 보다 크다면 Limit 부여
            //TotalUtil에서 크기 제한 설정 가능
            if (files.size() > totalUtil.getLimitOfFile()) {
                throw new ApiRequestException(totalUtil.messageForLimitOfFile());
            }
                //note에 file 도 더해서 넣어줌
                noteCreateDetail.uploadFile(files);

                //arrayId/title/content/endAt/step/
                return noteCreateDetail;
        }

        //해당 스텝에 노트가 하나도 없는 경우
        else
        {
            //프론트에서 받아낸 정보를 토대로 noteRepository에 저장, previous, next id, ArrayId 셋다 없으므로 0L(Long)
            /*
            들어가야할 목록
            title/content/endAt/step/user(글쓴이)/projectId/previousId(lastnoteid)/nextId(0)/배열Id
            */
            Note noteSave = noteRepository.save(Note
                    .of(noteDetail, endAt, step, currentUser, projectId,0L, 0L, 0L));

            //return을 위한 준비
            NoteCreateDto noteCreateDetail = NoteCreateDto.fromEntity(noteSave);


            //file관련 부분, 파일이름/파일id/유저/저장되는 노트
            files.stream()
                    .map(file -> new File(file.getFileName(), file.getFileUrl(), currentUser, noteSave))
                    .forEach(fileRepository::save);

            //file size가 5mb 보다 크다면 Limit 부여
            //TotalUtil에서 크기 제한 설정 가능
            if (files.size() > totalUtil.getLimitOfFile()) {
                throw new ApiRequestException(totalUtil.messageForLimitOfFile());
            }
                //note에 file 도 더해서 넣어줌
                noteCreateDetail.uploadFile(files);

                //arrayId/title/content/endAt/step/
                return noteCreateDetail;
        }
    }


    @Transactional
    public NoteDeleteDto deleteNote(Long projectId, NoteDeleteDetailDto noteDelete, User currentUser) {


        //현재의 id 및 전,후의 노트id를 가져옴, 없는 경우에는 NULL
        //첫 노트는 ArrayId와 projectId를 통해 찾아옴.
        //첫 노트를 기반으로 전 후의 노트를 찾아옴
        Note note = noteRepository.findByArrayIdAndProjectId(noteDelete.getArrayId(),projectId);
        Note previousNote = noteRepository.findByNoteId(note.getPreviousId()).orElse(null);
        Note nextNote = noteRepository.findByNoteId(note.getNextId()).orElse(null);


        //Case는 총 4가지로, 혼자이거나, 제일 앞이거나, 제일 뒤거나, 중간인 경우

        //혼자인 경우, 그냥 삭제

        //혼자가 아니며 제일 앞인 경우,
        if((note.getPreviousId() == 0L) && (note.getNextId() != 0L )) {
            //자동 업데이트
            nextNote.updatepreviousId(0L);

            //삭제될 노트와 같은 프로젝트id 노트중에서 ArrayId보다 큰 것 모두 -1
            noteRepository.updateArrayId(projectId,note.getArrayId());
        }

        //제일 뒤인 경우
        else if((note.getPreviousId() != 0L) && (note.getNextId() == 0L )) {
           //제일 마지막 note이므로 배열을 따로 조정할 필요가 없음
            previousNote.updatenextId(0L);
        }

        //중간인 경우, 현재 노트의 전,후의 id를 주어 전 후를 서로 이어줌
        else if((note.getPreviousId() != 0L) && (note.getNextId() != 0L )) {

            //앞, 뒤의 노드들을 서로 연결시켜준다.
            nextNote.updatepreviousId(note.getPreviousId());
            previousNote.updatenextId(note.getNextId());

            //삭제될 노트와 같은 프로젝트id 노트중에서 ArrayId보다 큰 것 모두 -1
            noteRepository.updateArrayId(projectId,note.getArrayId());
        }

        //Note에 연관된 파일 삭제
        fileRepository.deleteFileByNoteId(note.getNoteId());

        //noteId를 통해 자신 삭제
       noteRepository.deleteNoteById(note.getNoteId());

       //이외에 삭제할 Repository 추후 추가


        //위에서 이루어진 삭제가 db에 적용되고, 이후에 노트리스트를 다시 가져오기위해서
        //flush를 통해 쿼리를 날리고 clear를 통해 객체를 비우면서 commit
        em.flush();
        em.clear();

        List<Note> noteList = noteRepository.findbyProjectId(projectId);

        //ArrayId 기준으로 정렬
        List<Note> sortedList = noteList.stream()
                .sorted(Comparator.comparing(Note::getArrayId)).toList();

        return NoteDeleteDto.builder()
                .note(sortedList)
                .build();
    }


    //transaction 중 가장 엄격한 레벨의 어노테이션 , 동시성이 가장 높지만 다른 사람이 작업 중 건드릴 수 없음
    //칸반은 유저의 작업이라고 할만한 절차가 적으므로 괜찮음
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public NoteMoveDto moveNote(Long projectId, NoteMoveDetailDto noteMove, User currentUser){


        //현재의 id 및 전,후의 노트id를 가져옴, 없는 경우에는 NULL
        Note note = noteRepository.findByArrayIdAndProjectId(noteMove.getArrayId(), projectId);

        //projectid와 같은 것들 중에 getArrayId 초과 getNewArrayId이하 값 전부 -1
        noteRepository.updatemoveArrayId(projectId,noteMove.getArrayId(),noteMove.getNewArrayId());

        //새로운 step과 배열이 들어감
        note.updateMove(noteMove);

        //위에서 이루어진 삭제가 db에 적용되고, 이후에 노트리스트를 다시 가져오기위해서
        //flush를 통해 쿼리를 날리고 clear를 통해 객체를 비우면서 commit
        em.flush();
        em.clear();

        // Project로 전체 노트 리스트 가져오기
        List<Note> noteList = noteRepository.findByProjectId(projectId);

        //ArrayId 기준으로 정렬
        List<Note> sortedList = noteList.stream()
                .sorted(Comparator.comparing(Note::getArrayId)).toList();

        //전체 리스트를 재반환
        return NoteMoveDto.builder()
                .note(sortedList)
                .build();
    }

   public NoteFindMine findNote(Long projectId, User currentUser){

        //projectId와 userId로 해당 유저의 모든 노트리스트 얻음
        List<Note> noteList = noteRepository.findByProjectIdAndUserId(projectId,currentUser);

       //ArrayId 기준으로 정렬
       List<Note> sortedList = noteList.stream()
               .sorted(Comparator.comparing(Note::getArrayId)).toList();

       return NoteFindMine.builder()
               .note(sortedList)
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
        //List<Step> stepList = new ArrayList<>(Arrays.asList(Step.TODO, Step.DOING, Step.VERIFY, Step.DONE));

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
