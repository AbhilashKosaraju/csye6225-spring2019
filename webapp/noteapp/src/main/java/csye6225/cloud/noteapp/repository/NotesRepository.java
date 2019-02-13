package csye6225.cloud.noteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import csye6225.cloud.noteapp.model.Notes;

import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

}
