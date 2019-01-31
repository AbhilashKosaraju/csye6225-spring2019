package csye6225.cloud.noteapp.controller;

import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.User;
import csye6225.cloud.noteapp.repository.UserRepository;
import csye6225.cloud.noteapp.service.GetUserDetailsService;
import csye6225.cloud.noteapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GetUserDetailsService udService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() throws AppException {
        List<User> personList = userService.getAllUsers();
        return ResponseEntity.ok(personList);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) throws AppException {
        User u = userService.createUser(user);
        if(u != null)
            return ResponseEntity.ok("User created!!!");
        else
            return ResponseEntity.ok("User already exists.");
    }

    @GetMapping("/time")
    public String getTime(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        String email = udService.userName;
        if(userRepository.findUserByEmail(email)!=null)
            return date;
        else
            return "Unauthorized";
    }

    @PostMapping(value = "/login")
    public String verifyPerson(@Valid @RequestBody User user) throws AppException{
        List<User> userList = userService.getAllUsers();
        for(User u : userList){
            if(u.getEmail().equals(user.getEmail())){
                CharSequence match = user.getPassword();
                System.out.println(u.getPassword());
                if(passwordEncoder.matches(match,u.getPassword())){
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String date = timestamp.toString();
                    return date;
                }
            }
        }
        return "Invalid credentials. Please try again.";
    }

}
