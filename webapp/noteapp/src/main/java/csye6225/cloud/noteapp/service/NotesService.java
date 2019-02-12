package csye6225.cloud.noteapp.service;

import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.model.User;
import csye6225.cloud.noteapp.repository.NotesRepository;
import csye6225.cloud.noteapp.repository.UserRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NotesService {

    @Autowired
    private NotesRepository noteRepository;

    @Autowired
    private CustomUserDetailService udService;


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

    public Notes createNote(String title, String content) throws AppException {
        try {
            List<Notes> notesList  = getAllNotes();
            for (Notes n : notesList) {
                if (n.getTitle().equals(title)) {
                    return null;
                }
            }
            Notes newnote = new Notes();
            newnote.setTitle(title);
            newnote.setContent(content);
            newnote.setCreated_ts(new Date().toString());
            newnote.setUpdates_ts(new Date().toString());
            newnote.setUser_id(udService.user);

            return noteRepository.save(newnote);
        } catch (DataException e){
            throw new AppException(400, e.getMessage());
        } catch (Exception e) {
            throw new AppException("Error creating person");
        }
    }

}
