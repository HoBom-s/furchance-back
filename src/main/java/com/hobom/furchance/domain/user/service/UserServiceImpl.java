package com.hobom.furchance.domain.user.service;

import com.hobom.furchance.domain.user.repository.UserRepository;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import com.hobom.furchance.domain.user.dto.UserResponseDto;
import com.hobom.furchance.domain.user.dto.UserUpdateRequestDto;
import com.hobom.furchance.domain.user.entity.User;
import com.hobom.furchance.domain.auth.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordUtils passwordUtils;

    private final UserValidationService userValidationService;

    @Override
    public UserResponseDto getOneUserById(Long id) {

        return UserResponseDto.from(userValidationService.findOneUserById(id));
    }

    @Override
    public UserResponseDto updateOneUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {

        User foundUser = userValidationService.findOneUserById(id);

        String encodedPassword = passwordUtils.encodePassword(userUpdateRequestDto.getPassword());

        foundUser.setNickname(userUpdateRequestDto.getNickname());
        foundUser.setPassword(encodedPassword);

        User updatedUser = userRepository.save(foundUser);

        return UserResponseDto.from(updatedUser);
    }

    @Override
    public UserResponseDto removeOneUser(Long id) {

        User foundUser = userValidationService.findOneUserById(id);

        if (foundUser.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.ALREADY_DELETED + id);
        }

        foundUser.setDeleted(true);

        return UserResponseDto.from(foundUser);
    }
}
