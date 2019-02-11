package csye6225.cloud.noteapp.controller;

import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.model.User;
import csye6225.cloud.noteapp.repository.UserRepository;
import csye6225.cloud.noteapp.service.CustomUserDetailService;
import csye6225.cloud.noteapp.service.GetUserDetailsService;
import csye6225.cloud.noteapp.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailService udService;

    @Autowired
    private UserService userService;

    public static final Pattern email_pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    /*@GetMapping("/getallusers")
    public ResponseEntity<List<User>> getAllUsers() throws AppException {
        List<User> personList = userService.getAllUsers();
        return ResponseEntity.ok(personList);
    }*/

    @PostMapping(value = "/user/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) throws AppException, JSONException {

        HttpHeaders headers = new HttpHeaders();
        Matcher matcher = email_pattern.matcher(user.getEmail());
        boolean isEmail = matcher.find();

        if(!isEmail){

            JSONObject entity = new JSONObject();
            entity.put("Error","Not a valid email.");
            return new ResponseEntity<Object>(entity.toString(), HttpStatus.UNPROCESSABLE_ENTITY);

       /*     headers.add("Response-Code","422");
            headers.add("Response-Type","Unprocessable entity");
            return ResponseEntity.badRequest().headers(headers).body("{'error':'Not a valid email.'}");
            */

        }

        String password = user.getPassword();
        boolean hasLetter = false;
        boolean hasDigit = false;

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char x = password.charAt(i);
                if (Character.isLetter(x)) {

                    hasLetter = true;
                }

                else if (Character.isDigit(x)) {

                    hasDigit = true;
                }

                // no need to check further, break the loop
                if(hasLetter && hasDigit){

                    break;
                }

            }
            if (hasLetter && hasDigit) {
                System.out.println("STRONG");
            } else {
                JSONObject entity = new JSONObject();
                entity.put("Error","Not a strong Password.");
                return new ResponseEntity<Object>(entity.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
                /*
                headers.add("Response-Code","422");
                headers.add("Response-Type","Unprocessable entity");
                return ResponseEntity.badRequest().headers(headers).body("{'error':'Not a strong password.'}");
                */
            }
        } else {
            JSONObject entity = new JSONObject();
            entity.put("Error","Not a strong Password.");
            return new ResponseEntity<Object>(entity.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
            /*
            headers.add("Response-Code","422");
            headers.add("Response-Type","Unprocessable entity");
            return ResponseEntity.badRequest().headers(headers).body("{'error':'Not a strong password'}");
            */

        }


        User u = userService.createUser(user);
        if(u != null) {
            JSONObject entity = new JSONObject();
            entity.put("success","User created.");
            return new ResponseEntity<Object>(entity.toString(), HttpStatus.CREATED);
        }
        else{
            JSONObject entity = new JSONObject();
            entity.put("error","User already exists.");
            return new ResponseEntity<Object>(entity.toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/")
    public String getTime(){


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        String email = udService.user;
        System.out.println(email);
        if(userRepository.findUserByEmail(email)!=null){
            System.out.println("This is inisde tbe controller"+email);
            return date;}
        else
            return "Unauthorized";
    }

    @GetMapping("/note")
    public String getNotes(){
        String email = udService.user;
        System.out.println(email);
        if(userRepository.findUserByEmail(email).isPresent())
        {
            System.out.println("Inside the note request");
            return "Able to create a note";
        }
        else{
            return "Not authorized to create a note";
        }
//        return "Create a note";
    }

    @PostMapping(value= "/note", produces = "MediaType.APPLICATION_JSON_VALUE")
    public ResponseEntity<Object> createNote(@Valid @RequestBody Notes note){

        return null;

    }

    @GetMapping("/note/{id}")
    public Notes getNote( @PathVariable final int noteId){

        return null;
    }
    /*@PostMapping(value = "/login")
    public String verifyPerson(@Valid @RequestBody User user) throws AppException{
        List<User> userList = userService.getAllUsers();
        for(User u : userList){
            if(u.getEmail().equals(user.getEmail())){
                CharSequence match = user.getPassword();
                System.out.println(user.getPassword());
                System.out.println(u.getPassword());
                if(passwordEncoder.matches(match,u.getPassword())){
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String date = timestamp.toString();
                    return date;
                }
            }
        }
        return "Invalid credentials. Please try again.";
    }*/




}
