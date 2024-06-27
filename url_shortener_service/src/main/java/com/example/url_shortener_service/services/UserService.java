package com.example.url_shortener_service.services;

import com.example.url_shortener_service.entities.UserEntity;
import com.example.url_shortener_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = this.userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return optionalUser.map(userinfo -> {
            return userinfo;
        }).orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }
}
