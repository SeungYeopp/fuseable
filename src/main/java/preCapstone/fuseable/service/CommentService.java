package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.comment.*;
import preCapstone.fuseable.exception.ApiRequestException;
import preCapstone.fuseable.model.comment.Comment;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.comment.CommentRepository;
import preCapstone.fuseable.repository.note.NoteRepository;
import preCapstone.fuseable.repository.project.ProjectUserMappingRepository;
import preCapstone.fuseable.repository.user.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final CommentRepository commentRepository;
    private final ProjectUserMappingRepository projectUserMappingRepository;

    public CommentCreateDto createComment(Long noteId,  CommentCreateDetailDto commentCreateDetail) {

        User user = userRepository.findById(commentCreateDetail.getWriterId()).orElseThrow(
                () -> new ApiRequestException("등록되지 않은 유저의 접근입니다.")
        );

        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new ApiRequestException("생성되지 않은 노트입니다.")
        );

        Comment newComment = Comment.builder()
                .user(user)
                .note(note)
                .comment(commentCreateDetail.getContent())
                .build();

        Comment savedComment = commentRepository.save(newComment);

        return CommentCreateDto.builder()
                .content(savedComment.getComment())
                .commentId(savedComment.getCommentId())
                .writerId(savedComment.getUser().getUserCode())
                .build();
    }


    // 댓글 삭제
    @Transactional
    public CommentDeleteDto deleteComment(Long userId, Long commentId) {

        commentRepository.deleteByCommentIdAndUserId(commentId, userId);

        return CommentDeleteDto.builder()
                .commentId(commentId)
                .build();

    }
}