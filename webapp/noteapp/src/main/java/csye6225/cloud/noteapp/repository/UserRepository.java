package csye6225.cloud.noteapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import csye6225.cloud.noteapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String Email);
}


