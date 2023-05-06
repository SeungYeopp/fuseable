package preCapstone.fuseable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preCapstone.fuseable.controller.ScheduleController;
import preCapstone.fuseable.dto.schedule.ScheduleCreateDto;
import preCapstone.fuseable.dto.schedule.ScheduleReadAllDto;
import preCapstone.fuseable.dto.schedule.ScheduleReadDto;
import preCapstone.fuseable.dto.schedule.ScheduleUpdateDto;
import preCapstone.fuseable.repository.project.ProjectUserMappingRepository;
import preCapstone.fuseable.repository.schedule.ScheduleRepository;
import preCapstone.fuseable.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScheduleService {

    private final ProjectUserMappingRepository projectUserMappingRepository;

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleReposiotry;

    public ScheduleCreateDto createSchedule(Long userId) {

    }

    public ScheduleReadDto readSchedule(Long userId) {

    }
    public ScheduleUpdateDto updateSchedule(Long userId) {

    }
    public ScheduleReadAllDto readAllSchedule(Long userId) {

    }




}
