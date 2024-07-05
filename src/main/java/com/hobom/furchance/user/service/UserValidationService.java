package com.hobom.furchance.user.service;

import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.repository.UserRepository;
import com.mchange.util.AlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;

    public void validateNickname(String nickname) throws AlreadyExistsException {

        Optional<User> foundUser = userRepository.findByNickname(nickname);

        if(foundUser.isPresent()) {
            throw new AlreadyExistsException("Same nickname already exists");
        }
    }

    public User findOneUserById(Long id) {

        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public User findOneUserByNickname(String nickname) {

        return userRepository.findByNickname(nickname).orElseThrow(EntityNotFoundException::new);
    }

}
