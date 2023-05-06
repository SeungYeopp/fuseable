package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.note.*;
import preCapstone.fuseable.model.oauth.UserDetailsImpl;
import preCapstone.fuseable.service.NoteService;

@RequiredArgsConstructor
@RequestMapping("/api/project")
@RestController
public class NoteController {

    private final NoteService noteService;

    //note kanban CRUD

    //노트 생성
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/main/{userId}/{projectId}")
    public NoteCreateDto createNote(@PathVariable("projectId") Long projectId,@PathVariable("userId") Long userId,@RequestBody NoteCreateDetailDto noteCreateDetail) {
        return noteService.createNote(projectId, noteCreateDetail, userId);
    }

//    //노트 생성
//    @CrossOrigin(origins="*", allowedHeaders = "*")
//    @PostMapping("/{projectId}/main/{noteId}")
//    public NoteUpdateDto updateNote(@PathVariable("projectId") Long projectId, @PathVariable("noteId") Long noteId, @RequestBody NoteUpdateDetailDto noteUpdateDetail, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return noteService.updateNote(projectId, noteUpdateDetail, userDetails.getUser());
//    }

    //노트 삭제
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/main/delete/{projectId}/{arrayId}")
    public NoteDeleteDto deleteNote(@PathVariable("projectId") Long projectId,@PathVariable("arrayId") Long arrayId) {
        return noteService.deleteNote(projectId, arrayId);
    }

    //노트 업데이트, 노트 위치 변경
    //NoteMoveDto에는 전, 후의 id와 step(바뀔 곳) 필요
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/main/move/{projectId}")
    public NoteMoveDto moveNote(@PathVariable("projectId") Long projectId,@RequestBody NoteMoveDetailDto noteMove) {
        return noteService.moveNote(projectId, noteMove);
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/main/update/{projectId}/{arrayId}")
    public NoteUpdateDto updateNote(@PathVariable("projectId") Long projectId,@PathVariable("arrayId") Long arrayId, @RequestBody NoteUpdateDetailDto noteUpdate) {
        return noteService.updateNote(projectId, arrayId, noteUpdate);
    }

    //유저 이름으로 검색
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/main/mynote/{userId}/{projectId}")
    public NoteFindMine findNote(@PathVariable("userId") Long userId, @PathVariable("projectId") Long projectId) {
        return noteService.findNote(userId, projectId);
    }



    /*
    //노트읽기,프론트에서 처리한듯
    @GetMapping("/{projectId}")
    public NoteKanbanReadDto readKanban(@PathVariable("projectId") Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.readKanban(projectId, userDetails.getUser());
    }
     */

}
