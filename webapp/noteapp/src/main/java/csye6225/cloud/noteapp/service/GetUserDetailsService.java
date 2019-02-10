package csye6225.cloud.noteapp.service;

import csye6225.cloud.noteapp.model.User;
import csye6225.cloud.noteapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GetUserDetailsService implements UserDetailsService {

    public String userName;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        userName = username;
//        System.out.println(username);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findUserByEmail(username);
//
//
//        UserBuilder builder = null;
//        if(user != null){
//            builder = org.springframework.security.core.userdetails.User.withUsername(username);
//            builder.password(passwordEncoder.encode(user.getPassword()));
//            builder.roles("USER");
//        }
//        return builder.build();
        return null;
    }
}