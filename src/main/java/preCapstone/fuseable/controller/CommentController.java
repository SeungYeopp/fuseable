package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.comment.CommentCreateDto;
import preCapstone.fuseable.dto.comment.CommentDeleteDto;
import preCapstone.fuseable.dto.comment.CommentListDto;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.service.CommentService;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    //코멘트 작성
    @PostMapping("/api/comments/{noteId}")
    public CommentCreateDto createComment(@PathVariable Long noteId, User user, @RequestBody CommentCreateDto CommentCreateDto){
        return commentService.createComment(noteId, user, CommentCreateDto);
    }

    //코멘트 읽기
    @GetMapping("/api/comments/{noteId}")
    public CommentListDto readComments(@PathVariable Long noteId, User user){
        return commentService.readComments(noteId, user);
    }

    //코멘트 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public CommentDeleteDto deleteComment(@PathVariable Long commentId, User user) {
        return commentService.deleteComment(commentId, user);
    }
}
