package csye6225.cloud.noteapp.controller;

import com.google.gson.JsonObject;
import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.Attachment;
import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.repository.AttachmentRepository;
import csye6225.cloud.noteapp.service.AttachmentService;
import csye6225.cloud.noteapp.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class AttachmentController {

    @Autowired
    private AttachmentRepository ar;

    @Autowired
    private AttachmentService as;

    @Autowired
    private NotesService ns;

    @PostMapping("/note/{noteid}/attachment")
    public ResponseEntity<Object> addAttachments(@RequestParam("file") MultipartFile file, Authentication auth, @PathVariable final String noteid) throws AppException {

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

    @PutMapping("/note/{noteid}/attachment/{attachmentid}")
    public ResponseEntity<Object> updateAttachments(@RequestParam("file") MultipartFile file, Authentication auth, @PathVariable final String noteid, @PathVariable final String attachmentid) throws AppException {

        if (file.isEmpty()) {
            JsonObject entity = new JsonObject();
            entity.addProperty("Error","Please attach one file.");
            return ResponseEntity.badRequest().body(entity.toString());
        }
        Notes note = ns.findNotesById(noteid);
        if (note == null) {
            JsonObject entity = new JsonObject();
            entity.addProperty("Error","Note not found. Please enter a valid note id.");
            return ResponseEntity.badRequest().body(entity.toString());
        }
        if(auth.getName().equalsIgnoreCase(note.getUser_id())){
            /*int present = 0;
            for (Attachment a:note.getAttachments()) {
                if(a.getAttachment_id().equalsIgnoreCase(attachmentid)){
                    present = 1;
                    as.updateAttachment(file,note,a);
                }
            }
            if(present != 1){
                JsonObject entity = new JsonObject();
                entity.addProperty("Error", "Attachment not found. Please enter a valid attachment ID");
                return ResponseEntity.ok().body(entity.toString());
            }else{
                JsonObject entity = new JsonObject();
                entity.addProperty("Success", "Updated the attachment.");
                return ResponseEntity.status(204).body(entity.toString());
            }*/
            String status = as.updateAttachment(file,note,attachmentid);
            if(status == null){
                JsonObject entity = new JsonObject();
                entity.addProperty("Error", "Attachment not found. Please enter a valid attachment ID");
                return ResponseEntity.ok().body(entity.toString());
            }else{
                JsonObject entity = new JsonObject();
                entity.addProperty("Success", "Updated the attachment.");
                return ResponseEntity.status(204).body(entity.toString());
            }
        }else{
            JsonObject entity = new JsonObject();
            entity.addProperty("Error", "Access denied.");
            return ResponseEntity.status(401).body(entity.toString());
        }


    }

    @DeleteMapping("/note/{noteid}/attachment/{attachmentid}")
    public ResponseEntity<Object> deleteAttachments(Authentication auth, @PathVariable final String noteid, @PathVariable final String attachmentid) throws AppException {

        if(noteid == null || attachmentid == null){
            JsonObject entity = new JsonObject();
            entity.addProperty("Error", "Please enter a valid note & attachment id");
            return ResponseEntity.badRequest().body(entity.toString());
        }
        Notes note = ns.findNotesById(noteid);
        if (note == null) {
            JsonObject entity = new JsonObject();
            entity.addProperty("Error","Note not found. Please enter a valid note id");
            return ResponseEntity.badRequest().body(entity.toString());
        }
        if(auth.getName().equalsIgnoreCase(note.getUser_id())){
            int present = as.deleteAttachment(note,attachmentid);
            if(present != 1){
                JsonObject entity = new JsonObject();
                entity.addProperty("Error", "Attachment not found. Please enter a valid attachment ID");
                return ResponseEntity.ok().body(entity.toString());
            }else{
                JsonObject entity = new JsonObject();
                entity.addProperty("Success", "Deleted the attachment.");
                return ResponseEntity.status(204).body(entity.toString());
            }

        }else{
            JsonObject entity = new JsonObject();
            entity.addProperty("Error", "Access denied.");
            return ResponseEntity.status(401).body(entity.toString());
        }


    }

}
