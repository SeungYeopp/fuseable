package preCapstone.fuseable.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.dto.schedule.ScheduleReadAllDto;
import preCapstone.fuseable.dto.schedule.ScheduleReadDto;
import preCapstone.fuseable.dto.schedule.ScheduleUpdateDetailDto;
import preCapstone.fuseable.dto.schedule.ScheduleUpdateDto;
import preCapstone.fuseable.service.ScheduleService;

@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@RestController

public class ScheduleController {

    private final ScheduleService scheduleService;


    //read 겸 create
    //read 했을 시 userId로 된 시간표가 없으면 default생성 후 return
    //userId로 된 시간표가 있으면 해당 데이터 return
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/read/{userId}")
    public ScheduleReadDto readSchedule(@PathVariable("userId") Long userId) {
        return scheduleService.readSchedule(userId);
    }

    //업데이트
    //front에서 시간표 저장(업데이트) 버튼 누를 시 활성화
    //scheduleId를 통해 schedule의 checkbox 통으로 update
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/update/{scheduleId}")
    public ScheduleUpdateDto updateSchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleUpdateDetailDto scheduleUpdateDetail) {
        return scheduleService.updateSchedule(scheduleId, scheduleUpdateDetail);
    }

    //프로젝트 전부 읽기
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/readall/{projectId}")
    public ScheduleReadAllDto readAllSchedule(@PathVariable("projectId") Long projectId) {
        return scheduleService.readAllSchedule(projectId);

    }







}
