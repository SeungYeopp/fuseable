package preCapstone.fuseable.dto.project;

import lombok.Builder;
import lombok.Getter;


//controller에서 project id를 받아서 삭제
@Getter
public class ProjectDeleteDto {

    private Long projectId;

    @Builder
    public ProjectDeleteDto (Long projectId){
        this.projectId = projectId;
    }
}

