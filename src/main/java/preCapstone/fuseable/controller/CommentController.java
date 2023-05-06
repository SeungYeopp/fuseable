package preCapstone.fuseable.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.comment.CommentCreateDetailDto;

@RequiredArgsConstructor
@RequestMapping("/api/project")
@RestController
public class CommentController {


    //생성
    @CrossOrigin
    @PostMapping("/main/comment/{projectId}/{arrayId}")
    public CreateComment(@PathVariable("projectId") Long projectId, @PathVariable("arrayId") Long arrayId, @RequestBody CommentCreateDetailDto commentCreate)
        return commentservice.createComment(projectId, arrayId, commentCreate)

    //읽기
    @CrossOrigin
    @GetMapping("/main/comment/{projectId}/{arrayId}")
    public ReadComment(@PathVariable("projectId") Long projectId, @PathVariable("arrayId") Long arrayId, @RequestBody read)
        return commentservice.readComment()

    //삭제
    @CrossOrigin
    @GetMapping("/main/comment/{commentId}") //mapping과 pathvariable 수정 필요
    public DeleteComment(@PathVariable("projectId") Long commentId)
        return commentservice.deleteComment(commentId)



}
