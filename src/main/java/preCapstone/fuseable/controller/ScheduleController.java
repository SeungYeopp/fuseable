//package preCapstone.fuseable.controller;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import preCapstone.fuseable.dto.project.ProjectReadListDto;
//import preCapstone.fuseable.dto.schedule.ScheduleCreateDto;
//import preCapstone.fuseable.dto.schedule.ScheduleReadAllDto;
//import preCapstone.fuseable.dto.schedule.ScheduleReadDto;
//import preCapstone.fuseable.dto.schedule.ScheduleUpdateDto;
//import preCapstone.fuseable.service.ScheduleService;
//
//@RequiredArgsConstructor
//@RequestMapping("/api/schedule")
//@RestController
//
//public class ScheduleController {
//
//    private final ScheduleService scheduleService;
//
//    //필요한 사항
//    //1. 시간나누는 기준, 2. 어떤식으로 체크박스를 알려줄지 3. 보내줘야 하는것
//
//
//    @CrossOrigin(origins="*", allowedHeaders = "*")
//    @GetMapping("/create/{userId}")
//    public ScheduleCreateDto createSchedule(@PathVariable("userId") Long userId) {
//        return scheduleService.createSchedule(userId);
//    }
//
//    //CrossOrigin(origins="*", allowedHeaders = "*")
//    @GetMapping("/read/{userId}")
//    public ScheduleReadDto readSchedule(@PathVariable("userId") Long userId) {
//        return scheduleService.readSchedule(userId);
//
//    }
//
//    //CrossOrigin(origins="*", allowedHeaders = "*")
//    @GetMapping("/update/{userId}")
//    public ScheduleUpdateDto updateSchedule(@PathVariable("userId") Long userId) {
//        return scheduleService.updateSchedule(userId);
//    }
//
//    //CrossOrigin(origins="*", allowedHeaders = "*")
//    @GetMapping("/readall/{userId}")
//    public ScheduleReadAllDto readAllSchedule(@PathVariable("userId") Long userId) {
//        return scheduleService.readAllSchedule(userId);
//
//    }
//
//    //delete는 필요없을듯
//
//
//
//
//
//
//}
