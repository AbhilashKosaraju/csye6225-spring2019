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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private NotesService notesService;

    @GetMapping("/note")
    public List<Notes> getNotes(Authentication auth) throws AppException {
        List<Notes> notesList = notesService.getUserNotes(auth.getName());
        //JsonObject entity = new JsonObject();
        //entity.addProperty("Notes", notesList.toString());
        //return entity.toString();
        return notesList;
    }

    @PostMapping(value= "/note")
    public ResponseEntity<Object> createNote(@Valid @RequestBody Notes note,Authentication auth) throws AppException {
        String title = note.getTitle();
        String content = note.getContent();
        if(title != null && content != null) {
            Notes nt = notesService.createNote(title,content,auth.getName());
            if(nt != null) {
                JsonObject entity = new JsonObject();
                entity.addProperty("Success", "Note created");
                entity.addProperty("NoteID", nt.getNote_id().toString());
                return ResponseEntity.ok().body(entity.toString());
            }else{
                JsonObject entity = new JsonObject();
                entity.addProperty("Error","A note with same title already exists for this user. Please create another or update old one.");
                return ResponseEntity.badRequest().body(entity.toString());
            }
        }else {
            JsonObject entity = new JsonObject();
            entity.addProperty("Error","Please send the necessary parameters to store note.");
            return ResponseEntity.badRequest().body(entity.toString());
        }

    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Object> getNote(@PathVariable final String id,Authentication auth){
        UUID uuid = UUID.fromString(id);
        Notes note = notesService.findNotesById(uuid);
        if(note != null)
            if(auth.getName().equalsIgnoreCase(note.getUser_id())) {
                return ResponseEntity.ok().body(note.toString());
            }else{
                JsonObject entity = new JsonObject();
                entity.addProperty("Error", "Access denied.");
                return ResponseEntity.status(401).body(entity.toString());
            }
        else {
            JsonObject entity = new JsonObject();
            entity.addProperty("Error", "Note not found");
            return ResponseEntity.ok().body(entity.toString());
        }
    }

    @PutMapping("/note/{noteId}")
    public ResponseEntity<Object> updateNote(@RequestBody Notes note,@PathVariable final String noteId, Authentication auth){
        UUID uuid = UUID.fromString(noteId);
        Notes userNotes = notesService.findNotesById(uuid);
        Notes updated = null;
        if (userNotes == null) {
            JsonObject entity = new JsonObject();
            entity.addProperty("Error", "Note not found");
            return ResponseEntity.ok().body(entity.toString());
        }

        if(auth.getName().equalsIgnoreCase(userNotes.getUser_id())) {
            userNotes.setTitle(note.getTitle());
            userNotes.setContent(note.getContent());
            userNotes.setCreated_ts(userNotes.getCreated_ts());
            userNotes.setUpdates_ts(new Date().toString());
            updated = notesRepository.save(userNotes);

            if(updated != null) {
                JsonObject entity = new JsonObject();
                entity.addProperty("Success", "Note has been updated.");
                return ResponseEntity.ok().body(entity.toString());
            }else{
                JsonObject entity = new JsonObject();
                entity.addProperty("Error", "error updating the note. Look at logs for details");
                return ResponseEntity.badRequest().body(entity.toString());
            }
        }else{
            JsonObject entity = new JsonObject();
            entity.addProperty("Error", "Access denied.");
            return ResponseEntity.status(401).body(entity.toString());
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Object> deleteNote( @PathVariable final String id, Authentication auth){
        UUID uuid = UUID.fromString(id);
        Notes note = notesService.findNotesById(uuid);
        if(note != null) {
            if (auth.getName().equalsIgnoreCase(note.getUser_id())) {
                notesRepository.deleteById(uuid);
                JsonObject entity = new JsonObject();
                entity.addProperty("Success", "Deleted the note.");
                return ResponseEntity.ok().body(entity.toString());
            } else {
                JsonObject entity = new JsonObject();
                entity.addProperty("Error", "Access denied.");
                return ResponseEntity.status(401).body(entity.toString());
            }
        }else{
            notesRepository.delete(note);
            JsonObject entity = new JsonObject();
            entity.addProperty("Error", "Note not found. Please enter correct note id.");
            return ResponseEntity.ok().body(entity.toString());
        }
    }

}
