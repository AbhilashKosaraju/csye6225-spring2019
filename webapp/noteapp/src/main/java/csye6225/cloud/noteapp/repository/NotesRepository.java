package csye6225.cloud.noteapp.repository;

import csye6225.cloud.noteapp.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
}
