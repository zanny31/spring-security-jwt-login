package com.zanny.auth.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zanny.auth.user.domain.User;
import com.zanny.auth.user.domain.UserRole;
import com.zanny.auth.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(String username, String rawPassword) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다");
                });

        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .userRole(UserRole.USER)
                .build();

        return userRepository.save(user).getId();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
    }
}
