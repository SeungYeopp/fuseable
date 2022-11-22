package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.note.*;
import preCapstone.fuseable.model.oauth.UserDetailsImpl;
import preCapstone.fuseable.service.NoteService;

@RequiredArgsConstructor
@RequestMapping("")
@RestController
public class NoteController {

    private final NoteService noteService;

    //note kanban CRUD

    //노트 생성
    @PostMapping("/{projectId}/main")
    public NoteCreateDto createNote(@PathVariable("projectId") Long projectId,@RequestBody NoteCreateDetailDto noteCreateDetail, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.createNote(projectId, noteCreateDetail, userDetails.getUser());
    }

    //노트 생성
    @PostMapping("/{projectId}/main/{noteId}")
    public NoteUpdateDto updateNote(@PathVariable("projectId") Long projectId, @PathVariable("noteId") Long noteId, @RequestBody NoteUpdateDetailDto noteUpdateDetail, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.updateNote(projectId, noteUpdateDetail, userDetails.getUser());
    }

    //노트 삭제
    @DeleteMapping("/{projectId}/main")
    public NoteDeleteDto deleteNote(@PathVariable("projectId") Long projectId,@RequestBody NoteDeleteDetailDto noteDelete, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.deleteNote(projectId, noteDelete, userDetails.getUser());
    }

    //노트 업데이트, 노트 위치 변경
    //NoteMoveDto에는 전, 후의 id와 step(바뀔 곳) 필요
    @PutMapping("/{projectId}/main")
    public NoteMoveDto moveNote(@PathVariable("projectId") Long projectId,@RequestBody NoteMoveDetailDto noteMove, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.moveNote(projectId, noteMove, userDetails.getUser());
    }

    //유저 이름으로 검색
    @GetMapping("/{projectId}/mynotes")
    public NoteFindMine findNote(@PathVariable("projectId") Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.findNote(projectId, userDetails.getUser());
    }




    /*
    //노트읽기,프론트에서 처리한듯
    @GetMapping("/{projectId}")
    public NoteKanbanReadDto readKanban(@PathVariable("projectId") Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.readKanban(projectId, userDetails.getUser());
    }
     */

}
