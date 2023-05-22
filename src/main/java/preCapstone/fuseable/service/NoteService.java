package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import preCapstone.fuseable.config.TotalUtil;
import preCapstone.fuseable.dto.comment.CommentListDto;
import preCapstone.fuseable.dto.comment.CommentReadDto;
import preCapstone.fuseable.dto.file.FileDeleteDto;
import preCapstone.fuseable.dto.file.FileDownloadDto;
import preCapstone.fuseable.dto.file.FileUploadDetailDto;
import preCapstone.fuseable.dto.note.*;
import preCapstone.fuseable.dto.project.ProjectCrewDto;
import preCapstone.fuseable.exception.ApiRequestException;
import preCapstone.fuseable.model.comment.Comment;
import preCapstone.fuseable.model.file.File;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.note.Step;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.comment.CommentRepository;
import preCapstone.fuseable.repository.file.FileRepository;
import preCapstone.fuseable.repository.note.NoteRepository;
import preCapstone.fuseable.repository.project.ProjectRepository;
import preCapstone.fuseable.repository.project.ProjectUserMappingRepository;
import preCapstone.fuseable.repository.user.UserRepository;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;
    private final ProjectUserMappingRepository projectUserMappingRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TotalUtil totalUtil;
    private final FileRepository fileRepository;
    private final CommentRepository commentRepository;

    private final EntityManager em;

    private final CommentService commentService;

    //    private final ProjectUserMapping projectUserMapping;


    @Transactional
    public NoteCreateDto createNote(Long projectId, NoteCreateDetailDto noteCreateDetail, Long userId, List<MultipartFile> fileList) {

        //생성 프로세스
        //해당 프로젝트의
        //projectID를 통하여 해당 프로젝트와 관련된 노트리스트를 전부 받는다.
        Project project = projectRepository.findByOneProjectId(projectId);
        List<Note> noteList = noteRepository.findAllByProjectId(projectId);

        Step step = Step.valueOf(noteCreateDetail.getStep());
        LocalDate endAt = totalUtil.changeType(noteCreateDetail.getEndAt());
        LocalDate startAt = LocalDate.now();

        Note lastNote = totalUtil.getLastNote(noteList)
                .stream()
                .findFirst().orElse(null);

        User currentUser = userRepository.findByUserCode(userId);


        if (lastNote != null) {

            //프론트에서 받아낸 정보를 토대로 noteRepository에 저장, next id는 없으므로 비어있음
            /*
            들어가야할 목록
            title/content/endAt/step/user(글쓴이)/projectId/previousId(lastnoteid)/nextId(0)/배열Id
            */
            Note noteSave = noteRepository.save(Note
                    .of(noteCreateDetail, startAt, endAt, step, currentUser, project, lastNote.getNoteId(), 0L, lastNote.getArrayId() + 1,userId));

            //마지막 노트 다음 노트가 생겼으므로 이를 업데이트 해줌.
            lastNote.updatenextId(noteSave.getNoteId());

            //return을 위한 준비
            NoteCreateDto note = NoteCreateDto.fromEntity(noteSave);

            List<FileUploadDetailDto> files = new ArrayList<>();

            //file 관련 코드
            if(!CollectionUtils.isEmpty(fileList)) {

                //file size가 5보다 크다면 Limit 부여
                //TotalUtil에서 크기 제한 설정 가능
                if (fileList.size() > totalUtil.getLimitOfFile()) {
                    throw new ApiRequestException(totalUtil.messageForLimitOfFile());
                }

                int len = fileList.size();

                for (int i = 0; i < len; i++) {

                    //리스트 하나씩 빼내기
                    MultipartFile multipartFile = fileList.get(i);

                    //확장자명
                    String contentType = multipartFile.getContentType();

                    //원래 파일이름
                    String originalFilename = multipartFile.getOriginalFilename();

                    //랜덤 파일이름 (파일이름 중복 방지용)
                    String saveFileName = createSaveFileName(originalFilename);

                    String filePath = "C:/Users/Ku/" + saveFileName;

                    //데이터 저장
                    try {
                        multipartFile.transferTo((getFullPath(saveFileName)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    FileUploadDetailDto file = FileUploadDetailDto.fromEntity(originalFilename,filePath,saveFileName);

//                    //파일 하나 만들어서
//                    file.builder().fileName(originalFilename).fileUrl(filePath).fileRandomName(saveFileName).build();

                    //리스트에 저장
                    files.add(file);
                }


                //file관련 부분, 파일이름/파일id/유저/저장되는 노트
                files.stream()
                        .map(file -> new File(file.getFileName(), file.getFileUrl(),file.getFileRandomName(), noteSave))
                        .forEach(fileRepository::save);


                //note에 file 도 더해서 넣어줌
                note.uploadFile(files);
            }


            //arrayId/step/
            return note;
        }

        //해당 노트 리스트에 노트가 하나도 없는 경우
        else {
            //프론트에서 받아낸 정보를 토대로 noteRepository에 저장, previous, next id, ArrayId 셋다 없으므로 0L(Long)
            /*
            들어가야할 목록
            title/content/endAt/step/user(글쓴이)/projectId/previousId(lastnoteid)/nextId(0)/배열Id
            */
            Note noteSave = noteRepository.save(Note
                    .of(noteCreateDetail, startAt, endAt, step, currentUser, project, 0L, 0L, 0L,userId));

            List<FileUploadDetailDto> files = new ArrayList<>();


            //return을 위한 준비
            NoteCreateDto note = NoteCreateDto.fromEntity(noteSave);

            //file 관련 코드
            if(!CollectionUtils.isEmpty(fileList)) {

                int len = fileList.size();

                for (int i = 0; i < len; i++) {

                    //file size가 5보다 크다면 Limit 부여
                    //TotalUtil에서 크기 제한 설정 가능
                    if (fileList.size() > totalUtil.getLimitOfFile()) {
                        throw new ApiRequestException(totalUtil.messageForLimitOfFile());
                    }

                    //리스트 하나씩 빼내기
                    MultipartFile multipartFile = fileList.get(i);

                    //확장자명
                    String contentType = multipartFile.getContentType();

                    //원래 파일이름
                    String originalFilename = multipartFile.getOriginalFilename();

                    //랜덤 파일이름 (파일이름 중복 방지용)
                    String saveFileName = createSaveFileName(originalFilename);

                    String filePath = "C:/Users/Ku/" + saveFileName;

                    //데이터 저장
                    try {
                        multipartFile.transferTo((getFullPath(saveFileName)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    FileUploadDetailDto file = FileUploadDetailDto.fromEntity(originalFilename,filePath,saveFileName);

//                    //파일 하나 만들어서
//                    file.builder().fileName(originalFilename).fileUrl("C:/Users/"+ saveFileName).fileRandomName(saveFileName).build();

                    //리스트에 저장
                    files.add(file);
                }


                //file관련 부분, 파일이름/파일id/유저/저장되는 노트
                files.stream()
                        .map(file -> new File(file.getFileName(), file.getFileUrl(),file.getFileRandomName(),  noteSave))
                        .forEach(fileRepository::save);

                //note에 file 도 더해서 넣어줌
                note.uploadFile(files);
            }

            //arrayId/step/
            return note;
        }
    }


    @Transactional
    public NoteReadDto readNote(Long userId, Long projectId, Long noteId) {

        Project project = projectRepository.findByOneProjectId(projectId);

        Note note = noteRepository.findById(noteId).orElse(null);


        User user = userRepository.findById(userId).orElseThrow(
                () -> new ApiRequestException("등록되지 않은 유저의 접근입니다.")
        );

        List<Comment> commentList = commentRepository.findByNoteId(noteId);


//        List<CommentReadDto> commentReadEachDtoList =
//                commentList
//                        .stream()
//                        .map(e -> CommentReadDto.fromEntity(e))
//

        List<File> files = fileRepository.findByNoteId(noteId);


        return NoteReadDto.builder()
                .noteId(note.getNoteId())
                .title(note.getTitle())
                .content(note.getContent())
                .endAt(note.getEndAt())
                .startAt(note.getStartAt())
                .comments(commentList)
                .files(files)
                .build();
    }



    @Transactional
    public NoteDeleteDto deleteNote(Long projectId, Long arrayId) {


        //현재의 id 및 전,후의 노트id를 가져옴, 없는 경우에는 NULL
        //첫 노트는 ArrayId와 projectId를 통해 찾아옴.
        //첫 노트를 기반으로 전 후의 노트를 찾아옴
        Note note = noteRepository.findByArrayIdAndProjectId(arrayId,projectId);
        Note previousNote = noteRepository.findById(note.getPreviousId()).orElse(null);
        Note nextNote = noteRepository.findById(note.getNextId()).orElse(null);

        //        NoteResponseDto previousNote = noteRepository.findByNoteId(note.getPreviousId()).orElse(null);
        // NoteResponseDto nextNote = noteRepository.findByNoteId(note.getNextId()).orElse(null);


        //Case는 총 4가지로, 혼자이거나, 제일 앞이거나, 제일 뒤거나, 중간인 경우

        //혼자인 경우, 그냥 삭제

        //혼자가 아니며 제일 앞인 경우,
        if((note.getPreviousId() == 0L) && (note.getNextId() != 0L )) {
            //자동 업데이트
            nextNote.updatepreviousId(0L);

            //삭제될 노트와 같은 프로젝트id 노트중에서 ArrayId보다 큰 것 모두 -1
            noteRepository.updateArrayIdByProjectId(projectId,note.getArrayId());
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
            noteRepository.updateArrayIdByProjectId(projectId,note.getArrayId());
        }

        //Note에 연관된 파일 삭제
        fileRepository.deleteFileByNoteId(note.getNoteId());

        //noteId를 통해 자신 삭제
        noteRepository.deleteNoteById(note.getNoteId());

        commentRepository.deleteCommentByNoteId(note.getNoteId());

        //이외에 삭제할 Repository 추후 추가


        //위에서 이루어진 삭제가 db에 적용되고, 이후에 노트리스트를 다시 가져오기위해서
        //flush를 통해 쿼리를 날리고 clear를 통해 객체를 비우면서 commit
        em.flush();
        em.clear();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApiRequestException("회원을 조회할 프로젝트가 존재하지 않습니다."));

        List<ProjectUserMapping> userProjectMapping = projectUserMappingRepository.findAllByProject(project);

        List<Note> noteList = noteRepository.findAllByProjectId(projectId);

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
    public NoteMoveDto moveNote(Long projectId, NoteMoveDetailDto noteMove) {

        //projectId와 ArrayId를 통해서 해당 노트를 가져온다
        Note note = noteRepository.findByArrayIdAndProjectId(noteMove.getArrayId(), projectId);
        Step step = Step.valueOf(noteMove.getNewStep());


        //원래위치보다 뒤로 가는 경우
        if(noteMove.getArrayId() < noteMove.getNewArrayId()) {
            //projectid와 같은 것들 중에 getArrayId 초과 getNewArrayId이하 값 전부 -1
            noteRepository.updateMoveBackArrayId(projectId, noteMove.getArrayId(), noteMove.getNewArrayId());


            //새로운 step과 배열이 들어감
            note.updateMove(noteMove,step);

            //위에서 이루어진 삭제가 db에 적용되고, 이후에 노트리스트를 다시 가져오기위해서
            //flush를 통해 쿼리를 날리고 clear를 통해 객체를 비우면서 commit
//            em.flush();
//            em.clear();

            //전체 리스트를 재반환
            return NoteMoveDto.builder()
                    .newArrayId(noteMove.getNewArrayId())
                    .build();
        }

        //원래 위치보다 앞으로 가는 경우
        else if (noteMove.getArrayId() > noteMove.getNewArrayId()) {

            //projectid와 같은 것들 중에 getArrayId 초과 getNewArrayId이하 값 전부 -1
            noteRepository.updateMoveFrontArrayId(projectId, noteMove.getArrayId(), noteMove.getNewArrayId());

            //새로운 step과 배열이 들어감
            note.updateMove(noteMove,step);

            //위에서 이루어진 삭제가 db에 적용되고, 이후에 노트리스트를 다시 가져오기위해서
            //flush를 통해 쿼리를 날리고 clear를 통해 객체를 비우면서 commit
//            em.flush();
//            em.clear();

            //전체 리스트를 재반환
            return NoteMoveDto.builder()
                    .newArrayId(noteMove.getNewArrayId())
                    .build();
        }

        else {
            note.updateMove(noteMove,step);
            return NoteMoveDto.builder()
                    .newArrayId(noteMove.getNewArrayId())
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public NoteFindMine findNote(Long userId, Long projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApiRequestException("회원을 조회할 프로젝트가 존재하지 않습니다."));

        List<ProjectUserMapping> userProjectMapping = projectUserMappingRepository.findAllByProject(project);

        //크루원의 유저id/카카오닉네임/카카오사진을 list로 주게됨
        List<ProjectCrewDto> crewList = userProjectMapping.stream().map(
                        crew -> ProjectCrewDto.builder()
                                .userId(crew.getUser().getUserCode())
                                .userName(crew.getUser().getAccountNickname())
                                .userPicture(crew.getUser().getProfileImg())
                                .build())
                .collect(Collectors.toList());
        Optional<ProjectUserMapping> projectUserMapping
                = projectUserMappingRepository.findByUserIdAndProjectId(userId, projectId);
        //projectId와 userId로 해당 유저의 모든 노트리스트 얻음
        List<Note> noteList = noteRepository.findByProjectIdAndUserId(projectId,userId);

        //ArrayId 기준으로 정렬
        List<Note> sortedList = noteList.stream()
                .sorted(Comparator.comparing(Note::getArrayId)).toList();

        return NoteFindMine.fromEntity(sortedList);
    }


    @Transactional
    public NoteUpdateDto updateNote(Long userId, Long projectId, Long noteId, NoteUpdateDetailDto noteDetail, List<MultipartFile> fileList) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ApiRequestException("해당 노트가 존재하지 않습니다."));


        //새로 create 된 note의 step(String to Step)확인, date는 String to LocalDate, build를 위한 준비물
        LocalDate endAt = totalUtil.changeType(noteDetail.getEndAt());

        note.updateChanges(noteDetail, endAt);

        List<FileUploadDetailDto> files = new ArrayList<>();

        if (!CollectionUtils.isEmpty(fileList)) {

            int len = fileList.size();

            for (int i = 0; i < len; i++) {

                //리스트 하나씩 빼내기
                MultipartFile multipartFile = fileList.get(i);

                //확장자명
                String contentType = multipartFile.getContentType();

                //원래 파일이름
                String originalFilename = multipartFile.getOriginalFilename();

                //랜덤 파일이름 (파일이름 중복 방지용)
                String saveFileName = createSaveFileName(originalFilename);

                String filePath = "C:/Users/Ku/" + saveFileName;

                //데이터 저장
                try {
                    multipartFile.transferTo((getFullPath(saveFileName)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                FileUploadDetailDto file = FileUploadDetailDto.fromEntity(originalFilename, filePath, saveFileName);

//                    //파일 하나 만들어서
//                    file.builder().fileName(originalFilename).fileUrl("C:/Users/"+ saveFileName).fileRandomName(saveFileName).build();

                //리스트에 저장
                files.add(file);
            }
        }

        //file관련 부분, 파일이름/파일id/유저/저장되는 노트
        files.stream()
                .map(file -> new File(file.getFileName(), file.getFileUrl(), file.getFileRandomName(), note))
                .forEach(fileRepository::save);

        return NoteUpdateDto
                .builder()
                .noteId(noteId)
                .build();
    }

    @Transactional
    public FileDownloadDto fileDownload(Long fileId) {

        //질의문 하나만
        File file = fileRepository.findByFileId(fileId);



        return FileDownloadDto.builder()
                .fileName(file.getFileName())
                .fileUrl(file.getFileUrl())
                .fileRandomName(file.getFileRandomName())
                .build();

    }

    @Transactional
    public FileDeleteDto deleteFile(Long userId, Long noteId, Long fileId) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ApiRequestException("해당 노트가 존재하지 않습니다."));

        if(note.getUser().getUserCode()!= userId) {
            throw new ApiRequestException("글쓴이가 아닙니다.");
        }

        fileRepository.deleteById(fileId);

        return FileDeleteDto.builder().fileId(fileId).build();
    }


    private String createSaveFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자명 구하기
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // fullPath 만들기
    private Path getFullPath(String filename) {

        //파일이 실제로 저장될 위치
        Path uploadPath = Paths.get("C:/Users/Ku/" + filename);
        return uploadPath;
    }

    public List<Note> alarmNote(Long projectId) {
        Project project = projectRepository.findByOneProjectId(projectId);
        return noteRepository.alarmNote(projectId);
    }


//    @Transactional
//    public CommentCreateDto noteComment(Long projectId, Long arrayId, CommentCreateDetailDto noteDetail) {
//
//
//        Note note = noteRepository.findByArrayIdAndProjectId(arrayId, projectId);
//
//        NoteComment noteComment = noteCommentRepository.findByNoteId(note.getNoteId());
//
//
//
//        return CommentCreateDto.builder()
//                .arrayId(arrayId)
//                .comment(noteComment.getComment())
//                .writerId(noteComment.getWriterId())
//                .build();
//
//    }



    //    @Transactional
//    public NoteUpdateDto updateNote (Long projectId, NoteUpdateDetailDto noteDetail, User currentUser) {
//
//        Project project = projectRepository.findByOneProjectId(projectId);
//        Note note = noteRepository.findByArrayIdAndProjectId(noteDetail.getArrayId(),projectId);
//
//        //새로 create 된 note의 step(String to Step)확인, date는 String to LocalDate, build를 위한 준비물
//        Step step = Step.valueOf(noteDetail.getStep());
//        LocalDate endAt = totalUtil.changeType(noteDetail.getEndAt());
//
//        //file의 url, name
//        List<FileDetailDto> files = new ArrayList<>(noteDetail.getFiles());
//
//
//            //프론트에서 받아낸 정보를 토대로 noteRepository에 저장, next id는 없으므로 비어있음
//            /*
//            들어가야할 목록
//            title/content/endAt/step/user(글쓴이)/projectId/previousId(lastnoteid)/nextId(0)/배열Id
//            */
//            Note noteSave = noteRepository.save(Note
//                    .of(noteDetail, endAt, step, currentUser, project, note.getPreviousId(), note.getNextId(), note.getArrayId()));
//
//            //return을 위한 준비
//            NoteUpdateDto updatedNote = NoteUpdateDto.of(noteSave);
//
//            //file관련 부분, 파일이름/파일id/유저/저장되는 노트
//            files.stream()
//                    .map(file -> new File(file.getFileName(), file.getFileUrl(), currentUser, noteSave))
//                    .forEach(fileRepository::save);
//
//            //file size가 5mb 보다 크다면 Limit 부여
//            //TotalUtil에서 크기 제한 설정 가능
//            if (files.size() > totalUtil.getLimitOfFile()) {
//                throw new ApiRequestException(totalUtil.messageForLimitOfFile());
//            }
//                //note에 file 도 더해서 넣어줌
//                updatedNote.uploadFile(files);
//
//                //arrayId/title/content/endAt/step/file
//                return updatedNote;
//
//    }


    //front에서게 넘겨줄 데이터 옵션 중 하나, 상황봐야함
   /*
    @Transactional
    public NoteKanbanReadDto readKanban(Long projectId, User currentUser) {

        // Project로 전체 노트 리스트 가져오기
        List<Note> noteList = noteRepository.findAllByProject(projectUserMapping.getProject());

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