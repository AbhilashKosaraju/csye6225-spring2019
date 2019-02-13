package csye6225.cloud.noteapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import csye6225.cloud.noteapp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String Email);
}


