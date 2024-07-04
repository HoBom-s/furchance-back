package com.hobom.furchance.user.service;

import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.repository.UserRepository;
import com.mchange.util.AlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserValidationService {

    private UserRepository userRepository;

    public void validateNickname(String nickname) throws AlreadyExistsException {

        Optional<User> foundUser = userRepository.findByNickname(nickname);

        if(foundUser.isPresent()) {
            throw new AlreadyExistsException("Same nickname already exists");
        }
    }

    public User findOneUserById(Long id) {

        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
