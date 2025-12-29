package com.mateandgit.simple_sns.service;

import com.mateandgit.simple_sns.domain.User;
import com.mateandgit.simple_sns.dto.SignupRequest;
import com.mateandgit.simple_sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final List<String> FORBIDDEN_WORDS = List.of("운영자", "관리자", "admin");

    @Transactional
    public Long signup(SignupRequest request) {

        if (containsForbiddenWord(request.getNickname())) {
            throw new IllegalArgumentException("사용할 수 없는 닉네임입니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .build();

        return userRepository.save(user).getId();
    }
    private boolean containsForbiddenWord(String nickname) {
        return FORBIDDEN_WORDS.stream().anyMatch(nickname::contains);
    }
}
