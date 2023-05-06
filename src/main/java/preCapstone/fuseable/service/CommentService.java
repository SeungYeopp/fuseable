package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.comment.CommentCreateDto;
import preCapstone.fuseable.dto.comment.CommentDeleteDto;
import preCapstone.fuseable.dto.comment.CommentListDto;
import preCapstone.fuseable.dto.comment.CommentReadDto;
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

    public CommentCreateDto createComment(Long noteId, User cur_user, CommentCreateDto commentCreateDto) {
        User user = userRepository.findById(cur_user.getUserCode()).orElseThrow(
                () -> new ApiRequestException("등록되지 않은 유저의 접근입니다.")
        );

        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new ApiRequestException("생성되지 않은 노트입니다.")
        );

        Comment newComment = Comment.builder()
                .user(user)
                .note(note)
                .comment(commentCreateDto.getContent())
                .build();

        Comment savedComment = commentRepository.save(newComment);

        return CommentCreateDto.builder()
                .content(savedComment.getComment())
                .commentId(savedComment.getCommentId())
                .writer(savedComment.getUser().getUserCode())
                .build();
    }

    public CommentListDto readComments(Long noteId, User currentUser) {
        User user = userRepository.findById(currentUser.getUserCode()).orElseThrow(
                () -> new ApiRequestException("등록되지 않은 유저의 접근입니다.")
        );

        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new ApiRequestException("생성되지 않은 노트입니다.")
        );

        Project connectedProject = Optional.ofNullable(note.getProject()).orElseThrow(
                () -> new ApiRequestException("연결된 프로젝트가 없습니다.")
        );

        List<Comment> commentList = commentRepository.findByNoteId(noteId);
        List<CommentReadDto> commentReadEachDtoList =
                commentList
                        .stream()
                        .map(e -> CommentReadDto.fromEntity(e))
                        .collect(Collectors.toList());


        return CommentListDto.builder()
                .commentList(commentReadEachDtoList)
                .build();
    }


    // 댓글 삭제
    @Transactional
    public CommentDeleteDto deleteComment(Long commentId, User currentUser) {
        commentRepository.deleteByCommentIdAndUserId(commentId, currentUser.getUserCode());

        return CommentDeleteDto.builder()
                .commentId(commentId)
                .build();

    }
}
