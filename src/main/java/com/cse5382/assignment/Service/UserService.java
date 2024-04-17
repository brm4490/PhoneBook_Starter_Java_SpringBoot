package com.cse5382.assignment.Service;

import com.cse5382.assignment.AssignmentApplication;
import com.cse5382.assignment.Adapter.UserDetailsAdapter;
import com.cse5382.assignment.Model.UserEntry;
import com.cse5382.assignment.Repository.UserRepository;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    Logger logger = LogManager.getLogger(AssignmentApplication.class);

    @Autowired   
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserEntry> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntry addUser(UserEntry user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username (" + user.getUsername() + ") already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); //encode password before storing in DB
        return userRepository.save(user);
    
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .map(userEntry -> new UserDetailsAdapter(userEntry))
            .orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
    }
}