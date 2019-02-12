package csye6225.cloud.noteapp.controller;

import com.google.gson.JsonObject;
import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.model.User;
import csye6225.cloud.noteapp.repository.NotesRepository;
import csye6225.cloud.noteapp.repository.UserRepository;
import csye6225.cloud.noteapp.service.CustomUserDetailService;
import csye6225.cloud.noteapp.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NotesController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private CustomUserDetailService udService;

    @Autowired
    private NotesService notesService;

    @GetMapping("/note")
    public String getNotes() throws AppException {
        String email = udService.user;
        List<Notes> notesList = notesService.getAllNotes();
        JsonObject entity = new JsonObject();
        entity.addProperty("User ID", udService.user);
        entity.addProperty("Notes", notesList.toString());
        return entity.toString();
    }

    @PostMapping(value= "/note")
    public ResponseEntity<Object> createNote(@Valid @RequestBody Notes note) throws AppException {
        String title = note.getTitle();
        String content = note.getContent();
        if(title != null && content != null) {
            Notes nt = notesService.createNote(title,content);
            if(nt != null) {
                JsonObject entity = new JsonObject();
                entity.addProperty("Success", "Note created for " + udService.user);
                return ResponseEntity.ok().body(entity.toString());
            }else{
                JsonObject entity = new JsonObject();
                entity.addProperty("Error","A note with same title already exists. Please create another or update old one.");
                return ResponseEntity.badRequest().body(entity.toString());
            }
        }else {
            JsonObject entity = new JsonObject();
            entity.addProperty("Error","Please send the necessary parameters to store note.");
            return ResponseEntity.badRequest().body(entity.toString());
        }

    }

    @GetMapping("/note/{id}")
    public Notes getNote( @PathVariable final int noteId){

        return null;
    }

    @PutMapping("/note/{id}")
    public User updateUser(@PathVariable final int noteId){

        return null;

    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Void> deleteNote( @PathVariable final int noteId){

        return null;
    }

}
