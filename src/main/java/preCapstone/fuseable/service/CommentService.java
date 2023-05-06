package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.comment.CommentCreateDetailDto;
import preCapstone.fuseable.dto.comment.CommentCreateDto;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.comment.CommentRepository;
import preCapstone.fuseable.repository.note.NoteRepository;
import preCapstone.fuseable.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private final NoteRepository noteRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    public CommentCreateDto createComment(Long projectId, Long arrayId, CommentCreateDetailDto commentCreateDetail) {

        Note note = noteRepository.findByArrayIdAndProjectId(arrayId,projectId);



    }






}
