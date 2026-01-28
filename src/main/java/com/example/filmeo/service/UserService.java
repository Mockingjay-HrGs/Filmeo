package com.example.filmeo.service;

import com.example.filmeo.entity.User;
import com.example.filmeo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String pseudo, String email, String rawPassword) {
        if (userRepository.existsByPseudo(pseudo)) {
            throw new IllegalArgumentException("Pseudo déjà utilisé");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        User u = new User();
        u.setPseudo(pseudo);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setRole("ROLE_USER");
        u.setEnabled(true);
        u.setCreatedAt(LocalDateTime.now());

        userRepository.save(u);
    }
}
