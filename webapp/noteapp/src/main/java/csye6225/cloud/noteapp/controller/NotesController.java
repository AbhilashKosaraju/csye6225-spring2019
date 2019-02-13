package csye6225.cloud.noteapp.controller;

import com.google.gson.JsonObject;
import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.repository.NotesRepository;
import csye6225.cloud.noteapp.repository.UserRepository;
import csye6225.cloud.noteapp.service.NotesService;
import csye6225.cloud.noteapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class NotesController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserService udService;

    @Autowired
    private NotesService notesService;

    @GetMapping("/note")
    public String getNotes(Principal principal) throws AppException {
        List<Notes> notesList = notesService.getUserNotes(principal);
        JsonObject entity = new JsonObject();
        //entity.addProperty("User ID", udService.user);
        entity.addProperty("Notes", notesList.toString());
        return entity.toString();
    }

    @PostMapping(value= "/note")
    public ResponseEntity<Object> createNote(@Valid @RequestBody Notes note, Principal principal) throws AppException {
        String title = note.getTitle();
        String content = note.getContent();
        if(title != null && content != null) {
            Notes nt = notesService.createNote(title,content,principal);
            if(nt != null) {
                JsonObject entity = new JsonObject();
                //entity.addProperty("Success", "Note created for " + udService.user);
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
    public Notes getNote( @PathVariable final UUID id){

        Notes note = notesService.findNotesById(id);
        if(note != null)
           return note;
        else
            return new Notes();
    }

    @PutMapping("/note/{noteId}")
    public ResponseEntity<Object> updateUser(@RequestBody Notes note,@PathVariable  final UUID noteId){
        Notes userNotes = notesService.findNotesById(noteId);
        if (userNotes!=null){
            notesService.updateNotes(note,noteId);
            JsonObject entity = new JsonObject();
            entity.addProperty("Success","Note has been updated.");
            return ResponseEntity.badRequest().body(entity.toString());
        }
        else{
            System.out.println("This is the error");
            JsonObject entity = new JsonObject();
            entity.addProperty("Error","Please send the necessary parameters to store note.");
            return ResponseEntity.badRequest().body(entity.toString());
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Object> deleteNote( @PathVariable final UUID id){

        Notes note = notesService.findNotesById(id);
        notesRepository.delete(note);
        JsonObject entity = new JsonObject();
        entity.addProperty("Error","Please send the necessary parameters to store note.");
        return ResponseEntity.badRequest().body(entity.toString());
    }

}
