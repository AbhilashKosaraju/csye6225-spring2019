package csye6225.cloud.noteapp.controller;

import com.google.gson.JsonObject;
import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.repository.AttachmentRepository;
import csye6225.cloud.noteapp.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AttachmentController {

    @Autowired
    private AttachmentRepository ar;

    @Autowired
    private AttachmentService as;

    @Value("${spring.message}")
    private String message;

    @PostMapping("/note/{noteid}/attachment")
    public ResponseEntity<Object> singleFileUpload(@RequestParam("file") MultipartFile file, Authentication auth, @PathVariable final String noteid) throws AppException {

        if (file.isEmpty()) {
            JsonObject entity = new JsonObject();
            entity.addProperty("Error","Please attach one file.");
            return ResponseEntity.badRequest().body(entity.toString());
        }

        String attach = as.createAttachment(file, auth.getName(), noteid);

        if(attach != null){

            return ResponseEntity.status(201).body("");
        }else{
            JsonObject entity = new JsonObject();
            entity.addProperty("Error", "Access denied.");
            return ResponseEntity.status(401).body(entity.toString());
        }
    }

    @GetMapping("/note/message")
    public String HelloProfile(){
        return message;
    }

}
