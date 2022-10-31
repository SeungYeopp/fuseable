package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.project.Role;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProjectSelectDto {

    // 프로젝트 crew id
    private List<ProjectCrewDto> crewList;

    // 프로젝트 명
    private String title;

    // 프로젝트 안에 노트 중 가장 최근 수정 일자
    private LocalDateTime recentNoteUpdateDate;

    private Role role;

    private Note note;

    @Builder
    public ProjectSelectDto(List<ProjectCrewDto> crewList, String title,  LocalDateTime recentNoteUpdateDate,Role role, Note note) {
        this.crewList = crewList;
        this.title = title;
        this.recentNoteUpdateDate = recentNoteUpdateDate;
        this.role = role;
        this.note = note;
    }

    public static ProjectSelectDto fromEntity (ProjectDetailDto detail, ProjectCrewListDto crew, ProjectUserMapping role, Note note) {
        return ProjectSelectDto.builder()
                .crewList(crew.getCrews())
                .title(detail.getTitle())
                .recentNoteUpdateDate(detail.getRecentNoteUpdateDate())
                .role(role.getRole())
                .note(note)
                .build();
    }
}
