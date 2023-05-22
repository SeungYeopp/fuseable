package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.dto.schedule.*;
import preCapstone.fuseable.exception.ApiRequestException;
import preCapstone.fuseable.exception.ResourceNotFoundException;
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

        Schedule schedule = scheduleReposiotry.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 유저가 존재하지 않습니다."));

        schedule.updateCheckBox(scheduleUpdateDetail.getCheckBox());


        return ScheduleUpdateDto.builder()
                .checkBox(scheduleUpdateDetail.getCheckBox())
                .build();
    }

    public ScheduleReadAllDto readAllSchedule(Long projectId) {

        //projectId로부터 시작해 프로젝트멤버들을 찾는 내용
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApiRequestException("해당 프로젝트가 존재하지 않습니다."));

        List<ProjectUserMapping> projectUserMapping = projectUserMappingRepository.findAllByProject(project);

        List<ScheduleCrewDto> scheduleCrewList = projectUserMapping.stream().map(
                crew -> ScheduleCrewDto.builder()
                        .userId(crew.getUser().getUserCode())
                        .build()
        ).collect(Collectors.toList());

        //멤버의 숫자만큼 받고, 총집합 넣기
        int size = scheduleCrewList.size();
        String[] checkBoxes = new String[size];
        StringBuilder returnBox = new StringBuilder();

        for (int i = 0; i< size; i++) {
            ScheduleCrewDto Id = scheduleCrewList.get(i);
            checkBoxes[i] = scheduleReposiotry.findCheckBoxByUserId(Id.getUserId());
        }

        //각 멤버들의 시간시간을 check되었는지 확인하여 return할 checkbox를 만드는 loop
        for(int i = 0; i < 70; i++) {
            int check = 0;
            for (int j = 0; j<size; j++) {
                if(check == 1) break;

                if(checkBoxes[j].charAt(i) == '1') {
                    check = 1;
                    returnBox.append("1");
                }
            }
            if(check == 0) returnBox.append("0");
        }

        return ScheduleReadAllDto.builder()
                .checkBox(String.valueOf(returnBox))
                .build();







    }




}
