package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.project.ProjectCrewDto;
import preCapstone.fuseable.dto.schedule.ScheduleReadAllDto;
import preCapstone.fuseable.dto.schedule.ScheduleReadDto;
import preCapstone.fuseable.dto.schedule.ScheduleUpdateDetailDto;
import preCapstone.fuseable.dto.schedule.ScheduleUpdateDto;
import preCapstone.fuseable.exception.ApiRequestException;
import preCapstone.fuseable.model.project.Project;
import preCapstone.fuseable.model.project.ProjectUserMapping;
import preCapstone.fuseable.model.schedule.Schedule;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.project.ProjectRepository;
import preCapstone.fuseable.repository.project.ProjectUserMappingRepository;
import preCapstone.fuseable.repository.schedule.ScheduleRepository;
import preCapstone.fuseable.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScheduleService {

    private final ProjectUserMappingRepository projectUserMappingRepository;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleReposiotry;


    //사람이 있는지 findbyUserId해보고
    //있는지 없는지에 따라 분기를 나눠 default 생성후 return 혹은 저장된 것 return
    public ScheduleReadDto readSchedule(Long userId) {

        User user = userRepository.findByUserCode(userId);

        Optional<Schedule> schedule = scheduleReposiotry.findByUserId(userId);

        //있는 경우
        if(schedule.isPresent()) {

            return ScheduleReadDto.builder()
                    .scheduleId(schedule.get().getScheduleId())
                    .checkBox(schedule.get().getCheckBox()) //왜 get이 두번뜨지
                    .build();
        }

        //없는 경우
        else {
            String normal = "0000000000" +
                            "0000000000" +
                            "0000000000" +
                            "0000000000" +
                            "0000000000" +
                            "0000000000" +
                            "0000000000";

            Schedule newSchedule = Schedule.builder()
                    .user(user)
                    .checkBox(normal)
                    .build();

            scheduleReposiotry.save(newSchedule);

            return ScheduleReadDto.builder()
                    .scheduleId(newSchedule.getScheduleId())
                    .checkBox(newSchedule.getCheckBox())
                    .build();
        }


    }
    public ScheduleUpdateDto updateSchedule(Long scheduleId, ScheduleUpdateDetailDto scheduleUpdateDetail) {
        Optional<Schedule> schedule = scheduleReposiotry.findByIdandUpdateCheckBox(scheduleId);

        return ScheduleUpdateDto.builder()
                .checkBox(scheduleUpdateDetail.getCheckBox())
                .build();


    }
    public ScheduleReadAllDto readAllSchedule(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApiRequestException("해당 프로젝트가 존재하지 않습니다."));

        List<ProjectUserMapping> projectUserMapping = projectUserMappingRepository.findAllByProject(project);

        List<ProjectCrewDto> projectCrewDtoList = projectUserMapping.stream().map(
                crew -> ProjectCrewDto.builder()
                        .userId(crew.getUser().getUserCode())
                        .userName(crew.getUser().getKakaoNickname())
                        .userPicture(crew.getUser().getKakaoProfileImg())
                        .build()
        ).collect(Collectors.toList());

        




    }




}
