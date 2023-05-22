package preCapstone.fuseable.repository.file;

import org.springframework.data.jpa.repository.Modifying;
import preCapstone.fuseable.model.file.File;

import java.util.List;

public interface FileRepositoryQuerydsl {

    @Modifying(clearAutomatically = true)
    void deleteFileByNoteId(Long noteId);

    @Modifying(clearAutomatically = true)
    void deleteFileByProjectId(Long projectId);

    File findByFileId(Long fileId);

    List<File> findByNoteId(Long noteId);
}
