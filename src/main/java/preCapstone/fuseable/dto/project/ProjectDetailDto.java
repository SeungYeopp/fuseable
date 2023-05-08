package preCapstone.fuseable.dto.project;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectDetailDto {
    private Long projectId;

    // 프로젝트 명
    private String title;

    // 프로젝트 안에 노트 중 가장 최근 수정 일자
    private LocalDateTime recentNoteUpdateDate;


    public ProjectDetailDto(Long projectId, String title, LocalDateTime recentNoteUpdateDate) {
        this.projectId = projectId;
        this.title = title;
        this.recentNoteUpdateDate = recentNoteUpdateDate;
    }
}
