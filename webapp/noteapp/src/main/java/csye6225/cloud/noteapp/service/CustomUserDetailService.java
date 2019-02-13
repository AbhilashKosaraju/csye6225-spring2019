package csye6225.cloud.noteapp.service;

import csye6225.cloud.noteapp.model.CustomUserDetails;
import csye6225.cloud.noteapp.model.User;
import csye6225.cloud.noteapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    public String user;
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user= username;
        Optional<User> optionalUser = userRepository.findUserByEmail(username);
        optionalUser.orElseThrow(()->new UsernameNotFoundException("User email not found"));
        CustomUserDetails customUserDetails = optionalUser.map(user -> {
            return new CustomUserDetails(user);
        }).get();
        return customUserDetails;
    }
    }

