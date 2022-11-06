package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.note.NoteCreateDto;
import preCapstone.fuseable.dto.note.NoteDeleteDto;
import preCapstone.fuseable.dto.note.NoteDetailDto;
import preCapstone.fuseable.dto.note.NoteUpdateDto;
import preCapstone.fuseable.model.user.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {


    @Transactional
    public NoteCreateDto createNote (Long projectId, NoteDetailDto noteDetail, User currentUser) {

        return;
    }


    @Transactional
    public NoteDeleteDto deleteNote(Long noteId, User currentUser) {

        return;
    }

    @Transactional
    public NoteUpdateDto moveNote(Long noteId, User currentUser){

        return;
    }


}
