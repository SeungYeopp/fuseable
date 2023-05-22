package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import preCapstone.fuseable.dto.file.FileDeleteDto;
import preCapstone.fuseable.dto.file.FileDownloadDto;
import preCapstone.fuseable.dto.note.*;
import preCapstone.fuseable.model.note.Note;
import preCapstone.fuseable.service.NoteService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/project")
@RestController
public class NoteController {
//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
//        multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); // 파일당 업로드 크기 제한 (5MB)
//        return multipartResolver;
//    }

    private final NoteService noteService;

    //note kanban CRUD

    //노트 생성
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping(value = "/main/{userId}/{projectId}",consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public NoteCreateDto createNote(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId, @RequestPart(value = "contents") NoteCreateDetailDto noteCreateDetail, @RequestPart(value = "file", required=false) List<MultipartFile> multipartFileList ) {
        return noteService.createNote(projectId, noteCreateDetail, userId, multipartFileList);
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/main/read/{userId}/{projectId}/{noteId}")
    public NoteReadDto readNote(@PathVariable("userId") Long userId, @PathVariable("projectId") Long projectId, @PathVariable("noteId") Long noteId){
        return noteService.readNote(userId,projectId, noteId);
    }

    //노트 삭제
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/main/delete/{projectId}/{arrayId}")
    public NoteDeleteDto deleteNote(@PathVariable("projectId") Long projectId,@PathVariable("arrayId") Long arrayId) {
        return noteService.deleteNote(projectId, arrayId);
    }

    //노트 업데이트, 노트 위치 변경
    //NoteMoveDto에는 전, 후의 id와 step(바뀔 곳) 필요
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/main/move/{projectId}")
    public NoteMoveDto moveNote(@PathVariable("projectId") Long projectId,@RequestBody NoteMoveDetailDto noteMove) {
        return noteService.moveNote(projectId, noteMove);
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping(value = "/main/update/{userId}/{projectId}/{noteId}",consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public NoteUpdateDto updateNote(@PathVariable("userId") Long userId,
                                    @PathVariable("projectId") Long projectId,
                                    @PathVariable("noteId") Long noteId,
                                    @RequestPart(value = "contents") NoteUpdateDetailDto noteUpdate,
                                    @RequestPart(value = "file", required=false) List<MultipartFile> multipartFileList ) {

        return noteService.updateNote(userId, projectId, noteId, noteUpdate, multipartFileList);
    }

    //유저 이름으로 검색
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/main/mynote/{userId}/{projectId}")
    public NoteFindMine findNote(@PathVariable("userId") Long userId, @PathVariable("projectId") Long projectId) {
        return noteService.findNote(userId, projectId);
    }

//    @CrossOrigin
//    @GetMapping("/main/note/{fileId}")
//    public FileDownloadDto fileDownload(@PathVariable("fileId") Long fileId) {
//        return noteService.fileDownload(fileId);
//    }

//    @PostMapping("/main/note/download")
//    public void fileDownload(@RequestBody FileDownloadDto fileDownload, HttpServletResponse response) throws IOException {
//
//        String path = fileDownload.getFileUrl();
//        File file = new File(path);
//        byte[] fileByte = FileUtils.readFileToByteArray(new File(fileDownload.getFileUrl()));
//
//
//        if(file == null) throw new FileNotFoundException("파일이 없습니다");
//
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment; filename=\""+ file.getName() +"\"");
//        response.setContentLength(fileByte.length);
//        FileCopyUtils.copy(fileByte, response.getOutputStream());
//    }


    @PostMapping("/main/note/download")
    public void fileDownload(@RequestBody FileDownloadDto fileDownload, HttpServletResponse response) throws IOException {

        String path = fileDownload.getFileUrl();
        File file = new File(path);
        byte[] fileByte = FileUtils.readFileToByteArray(new File(fileDownload.getFileUrl()));
        long fileLength = file.length();


        if (file == null) throw new FileNotFoundException("파일이 없습니다");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        FileCopyUtils.copy(fileByte, response.getOutputStream());
    }

//    @CrossOrigin
//    @PostMapping("/main/note/download")
//    public ResponseEntity<Object> download(@RequestBody FileDownloadDto fileDownloadDto) {
//
//        String path = "C:/Users/Ku/d.png";
//        try {
//            Path filePath = Paths.get(path);
//            Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
//
//            File file = new File(path);
//
//            if(!file.exists()) {
//                throw new FileNotFoundException("파일이 없습니다.");
//            }
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("logo111.png").build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
//
//            System.out.println(path);
//            System.out.println(file.getName());
//
//            return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
//        } catch(Exception e) {
//            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
//        }
//
//    }
//    @CrossOrigin(origins="*", allowedHeaders = "*")
//    @PostMapping("/main/note/download")
//    public void fileDownload(@RequestBody FileDownloadDto fileDownload, HttpServletResponse response) {
////        byte[] fileByte = FileUtils.readFileToByteArray(new File(fileDownload.getFileUrl()));
//
//        String fileName = fileDownload.getFileRandomName();
//        String fileUrl = fileDownload.getFileUrl();
//
//        int pos = fileDownload.getFileName().lastIndexOf(".");
//        String contentType= fileDownload.getFileName().substring(pos+1);
//
//        File file = new File(fileUrl);
//        long fileLength = file.length();
//
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
//        response.setHeader("Content-Transfer-Encoding", "binary");
//        response.setHeader("Content-Type", contentType);
//        response.setHeader("Content-Length", "" + fileLength);
//        response.setHeader("Pragma", "no-cache;");
//        response.setHeader("Expires", "-1;");
//
//        try(
//                FileInputStream fis = new FileInputStream(fileUrl);
//                OutputStream out = response.getOutputStream();
//        ){
//            int readCount = 0;
//            byte[] buffer = new byte[1024];
//            while((readCount = fis.read(buffer)) != -1){
//                out.write(buffer,0,readCount);
//            }
//        }catch(Exception ex){
//            throw new RuntimeException("file Save Error");
//        }
//
////        response.setContentType("application/octet-stream");
////        response.setContentLength(fileByte.length);
////        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileDownload.getFileName() + "1", "UTF-8")+"\";");
////        response.setHeader("Content-Transfer-Encoding", "binary");
//
////        response.getOutputStream().write(fileByte);
////        response.getOutputStream().flush();
////        response.getOutputStream().close();
//    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @GetMapping("/main/mynote/{userId}/{noteId}/{fileId}")
    public FileDeleteDto deleteFile(@PathVariable("userId") Long userId, @PathVariable("noteId") Long noteId, @PathVariable("fileId") Long fileId) {

        return noteService.deleteFile(userId, noteId, fileId);
    }

    @CrossOrigin
    @GetMapping("/note/alarmNote/{projectId}")
    public List<Note> alarmNote(@PathVariable("projectId") Long projectId) {
        return noteService.alarmNote(projectId);
    }

    /*
    //노트읽기,프론트에서 처리한듯
    @GetMapping("/{projectId}")
    public NoteKanbanReadDto readKanban(@PathVariable("projectId") Long projectId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return noteService.readKanban(projectId, userDetails.getUser());
    }
     */

}
