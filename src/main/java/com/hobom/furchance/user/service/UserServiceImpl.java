package com.hobom.furchance.user.service;

import com.hobom.furchance.user.dto.UserCreateRequestDto;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.dto.UserUpdateRequestDto;
import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createOneUser(UserCreateRequestDto userCreateRequestDto) {

        isNicknameValid(userCreateRequestDto.getNickname());

        String encodedPassword = encodePassword(userCreateRequestDto.getPassword());

        User createdUser = userRepository.save(User.of(userCreateRequestDto.getNickname(), encodedPassword));

        return UserResponseDto.from(createdUser);
    }

    @Override
    public UserResponseDto getOneUser(Long id) {

        return UserResponseDto.from(findOneUserById(id));
    }

    @Override
    public UserResponseDto updateOneUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {

        User foundUser = findOneUserById(id);

        String encodedPassword = encodePassword(userUpdateRequestDto.getPassword());

        foundUser.setNickname(userUpdateRequestDto.getNickname());
        foundUser.setPassword(encodedPassword);

        User updatedUser = userRepository.save(foundUser);

        return UserResponseDto.from(updatedUser);
    }

    @Override
    public UserResponseDto removeOneUser(Long id) {

        User foundUser = findOneUserById(id);

        if(foundUser.isDeleted()) {
            // @Todo customize Exception
            throw new RuntimeException("Failed: Already deleted user");
        }

        foundUser.setDeleted(true);

        return UserResponseDto.from(foundUser);
    }

    private void isNicknameValid(String nickname) {

        Optional<User> foundUser = userRepository.findByNickname(nickname);

        if(foundUser.isPresent()) {
            throw new EntityExistsException("Same nickname already exists");
        }
    }

    private User findOneUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
