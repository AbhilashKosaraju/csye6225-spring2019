package csye6225.cloud.noteapp.service;

import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.repository.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import csye6225.cloud.noteapp.model.User;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAllUsers() throws AppException {
        try {
            List<User> userList = userRepository.findAll();
            return userList;
        } catch (DataException e){
            throw new AppException(400, e.getMessage());
        } catch (Exception e) {
            throw new AppException("Error getting all users");
        }
    }

    public User createUser(User user) throws AppException {
        try {
            System.out.println(user.getPassword());

            List<User> userList = getAllUsers();
            for (User u : userList) {
                if (u.getEmail().equals(user.getEmail())) {
                    return null;
                }
            }
            User newuser = new User();
            newuser.setEmail(user.getEmail());
            newuser.setPassword(passwordEncoder.encode(user.getPassword()));
            //newuser.setNote(user.getNote());

            System.out.println(newuser.getPassword());

            return userRepository.save(newuser);
        } catch (DataException e){
            throw new AppException(400, e.getMessage());
        } catch (Exception e) {
            throw new AppException("Error creating person");
        }
    }

}
