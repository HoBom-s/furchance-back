package com.hobom.furchance.domain.user.service;

import com.hobom.furchance.domain.user.repository.UserRepository;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import com.hobom.furchance.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserValidationService {

    private final UserRepository userRepository;

    public void validateNickname(String nickname) {

        Optional<User> foundUser = userRepository.findByNickname(nickname);

        if (foundUser.isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, ErrorMessage.ALREADY_EXISTS);
        }
    }

    public User findOneUserById(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND + id));
    }

    public User findOneUserByNickname(String nickname) {

        return userRepository.findByNickname(nickname).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND + nickname));
    }

}
