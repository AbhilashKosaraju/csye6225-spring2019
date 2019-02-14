package csye6225.cloud.noteapp.service;

import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.repository.NotesRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.*;

@Component
public class NotesService {

    @Autowired
    private NotesRepository noteRepository;

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

    public Notes createNote(String title, String content,String name) throws AppException {
        try {
            List<Notes> notesList  = getAllNotes();
            for (Notes n : notesList) {
                if (n.getTitle().equalsIgnoreCase(title) && n.getUser_id().equalsIgnoreCase(name)) {
                    return null;
                }
            }
            Notes newnote = new Notes();
            UUID uuid = UUID.randomUUID();
            newnote.setNote_id(uuid.toString());
            newnote.setTitle(title);
            newnote.setContent(content);
            newnote.setCreated_ts(new Date().toString());
            newnote.setUpdates_ts(new Date().toString());
            newnote.setUser_id(name);

            return noteRepository.save(newnote);
        } catch (DataException e){
            throw new AppException(400, e.getMessage());
        } catch (Exception e) {
            throw new AppException("Error creating note");
        }
    }


    public Notes findNotesById(String noteId){
        Iterable<Notes> notesList = noteRepository.findAll();
        Notes notes = null;
        for(Notes note : notesList){
            if(note.getNote_id().equalsIgnoreCase(noteId)){
                notes = note;
            }
        }
        return notes;
    }

    public List<Notes> getUserNotes(String user) throws AppException{
        try{
            List<Notes> notesList = getAllNotes();
            List<Notes> userNotes = new ArrayList<Notes>();
            for(Notes note : notesList) {
                if (note.getUser_id().equalsIgnoreCase(user))
                    userNotes.add(note);
            }
            return userNotes;
        } catch (Exception e){
            throw new AppException(400,e.getMessage());
        }
    }

}
