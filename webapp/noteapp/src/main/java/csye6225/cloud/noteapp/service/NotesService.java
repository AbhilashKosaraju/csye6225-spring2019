package csye6225.cloud.noteapp.service;

import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.repository.NotesRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class NotesService {

    @Autowired
    private NotesRepository noteRepository;

    @Autowired
    private UserService userService;


    public List<Notes> getAllNotes() throws AppException {
        try {
            List<Notes> notesList = noteRepository.findAll();
            return notesList;
        } catch (DataException e){
            throw new AppException(400, e.getMessage());
        } catch (Exception e) {
            throw new AppException("Error getting all notes");
        }
    }

    public List<Notes> getUserNotes( Principal principal) throws AppException{
        try{
            Iterable<Notes> notesList = noteRepository.findAll();
            List<Notes> userNotes = new ArrayList<Notes>();
            for(Notes note : notesList) {
                if (note.getUser_id().equals(principal.getName()))
                    userNotes.add(note);
            }
            return userNotes;
        } catch (Exception e){
            throw new AppException(400,e.getMessage());
        }
    }

    public Notes createNote(String title, String content, Principal principal) throws AppException {
        try {
            List<Notes> notesList  = getAllNotes();
            for (Notes n : notesList) {
                if (n.getTitle().equals(title)) {
                    return null;
                }
            }
            Notes newnote = new Notes();
            UUID uuid = UUID.randomUUID();
            newnote.setNote_id(uuid);
            newnote.setTitle(title);
            newnote.setContent(content);
            newnote.setCreated_ts(new Date().toString());
            newnote.setUpdates_ts(new Date().toString());
            newnote.setUser_id(principal.getName());
            return noteRepository.save(newnote);
        } catch (DataException e){
            throw new AppException(400, e.getMessage());
        } catch (Exception e) {
            throw new AppException("Error creating person");
        }
    }


    public Notes findNotesById(UUID noteId){
        Iterable<Notes> notesList = noteRepository.findAll();
        Notes notes = null;
        for(Notes note : notesList){
            if(note.getNote_id().equals(noteId)){
                notes = note;
            }
        }
        return notes;

    }

    public void updateNotes(Notes not, UUID id){
        Iterable<Notes> notesList = noteRepository.findAll();
        for(Notes note : notesList){
            if(note.getNote_id().equals(id)){
                note.setTitle(not.getTitle());
//                note.setNote_id(not.getNote_id());
                note.setContent(not.getContent());
                note.setCreated_ts(not.getCreated_ts());
                note.setUpdates_ts(not.getUpdates_ts());
                noteRepository.save(note);
                return;
            }
        }
    }

}
