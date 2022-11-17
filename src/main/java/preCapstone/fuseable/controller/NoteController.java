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
    @PostMapping("/{projectId}")
    public NoteCreateDto createNote(@PathVariable Long projectId, @RequestBody NoteDetailDto noteDetail, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.createNote(projectId, noteDetail, userDetails.getUser());
    }

    //노트 삭제
    @DeleteMapping("/{projectId}")
    public NoteDeleteDto deleteNote(@PathVariable Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.deleteNote(projectId, userDetails.getUser());
    }

    //노트 업데이트, 노트 위치 변경
    //NoteMoveDto에는 전, 후의 id와 step(바뀔 곳) 필요
    @PutMapping("{projectId}")
    public NoteMoveDto moveNote(@PathVariable Long noteId,
                                      @RequestBody NoteMoveDetailDto noteMove,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.moveNote(noteId,noteMove, userDetails.getUser());
    }

    /*
    //노트 수정
    @PutMapping("/notes/details/{noteId}")
    public NoteUpdateResponseDto updateNote(@PathVariable("noteId") Long noteId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody NoteUpdateRequestDto noteUpdateRequestDto) {
        return noteService.updateNoteDetail(noteId, userDetails.getUser(), noteUpdateRequestDto);
        //  서비스의 메소드명은 변경될수있습니다.


    /*
    //노트읽기,프론트에서 처리한듯
    @GetMapping("/{projectId}")
    public NoteKanbanReadDto readKanban(@PathVariable("projectId") Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.readKanban(projectId, userDetails.getUser());
    }
     */





}