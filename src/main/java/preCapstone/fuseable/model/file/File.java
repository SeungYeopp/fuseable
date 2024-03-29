package preCapstone.fuseable.model.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.user.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class File {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    @Column(name = "FILE_ID")
    private Long fileId;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "File_Random_Name")
    private String fileRandomName;

    @Column(name = "FILE_URL")
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Note_ID")
    private Note note;

    @Builder
    public File(String fileName, String fileRandomName, String fileUrl, Note note) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileRandomName = fileRandomName;
        this.note = note;
    }

}