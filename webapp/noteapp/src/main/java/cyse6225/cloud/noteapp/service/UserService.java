package csye6225.cloud.noteapp.service;

import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.repository.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import csye6225.cloud.noteapp.model.User;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
            System.out.println(user.toString());

            List<User> userList = getAllUsers();
            for (User u : userList) {
                if (u.getEmail().equals(user.getEmail())) {
                    return null;
                }
                break;
            }
            byte[] bytesEncoded = Base64.encodeBase64(user.getPassword().getBytes());
            String pass = new String(bytesEncoded);
            User newuser = new User();
            newuser.setEmail(user.getEmail());
            newuser.setPassword(pass);
            return userRepository.save(newuser);
        } catch (DataException e){
            //LOG.error(e.getMessage());
            throw new AppException(400, e.getMessage());
        } catch (Exception e) {
            //LOG.error("Error creating person", e);
            throw new AppException("Error creating person");
        }
    }

}
