package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.project.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class ProjectSelectDto {

    // 프로젝트 crew id
    private List<ProjectCrewDto> crewList;

    // 프로젝트 명
    private String title;


    private Role role;

    private List<Note> note;

    @Builder
    public ProjectSelectDto(List<ProjectCrewDto> crewList, String title,Role role, List<Note> note) {
        this.crewList = crewList;
        this.title = title;
        this.role = role;
        this.note = note;
    }

    public static ProjectSelectDto fromEntity (List<ProjectCrewDto> crew, Optional<ProjectUserMapping> projectDetail, List<Note> noteList) {
        return ProjectSelectDto.builder()
                .crewList(crew)
                .title(projectDetail.get().getProject().getTitle())
                .role(projectDetail.get().getRole())
                .note(noteList)
                .build();
    }
}
