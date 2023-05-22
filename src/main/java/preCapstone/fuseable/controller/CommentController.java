package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.comment.CommentCreateDetailDto;
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
    public CommentCreateDto createComment(@PathVariable Long noteId, @RequestBody CommentCreateDetailDto CommentCreateDetail){
        return commentService.createComment(noteId, CommentCreateDetail);
    }

    //코멘트 삭제
    @DeleteMapping("/api/comments/{userId}/{commentId}")
    public CommentDeleteDto deleteComment(@PathVariable("userId") Long userId, @PathVariable("commentId") Long commentId) {
        return commentService.deleteComment(userId, commentId);
    }
}
