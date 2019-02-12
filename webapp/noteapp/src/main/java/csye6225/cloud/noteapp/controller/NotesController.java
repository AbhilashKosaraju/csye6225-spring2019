package csye6225.cloud.noteapp.controller;

import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.model.User;
import csye6225.cloud.noteapp.repository.NotesRepository;
import csye6225.cloud.noteapp.repository.UserRepository;
import csye6225.cloud.noteapp.service.CustomUserDetailService;
import csye6225.cloud.noteapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class NotesController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailService udService;

    @Autowired
    private UserService userService;

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
    }

    @PostMapping(value= "/note")
    public ResponseEntity<Object> createNote(@Valid @RequestBody Notes note){

        return null;

    }

    @GetMapping("/note/{id}")
    public Notes getNote( @PathVariable final int noteId){

        return null;
    }

    @PutMapping("/note/{id}")
    public User updateUser(@PathVariable final int noteId){

        return null;

    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Void> deleteNote( @PathVariable final int noteId){

        return null;
    }

}
