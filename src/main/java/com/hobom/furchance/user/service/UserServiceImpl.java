package com.hobom.furchance.user.service;

import com.hobom.furchance.auth.dto.SignUpRequestDto;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.dto.UserUpdateRequestDto;
import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.repository.UserRepository;
import com.hobom.furchance.auth.util.JwtUtils;
import com.hobom.furchance.auth.util.PasswordUtils;
import com.mchange.util.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordUtils passwordUtils;

    private final UserValidationService userValidationService;

    private final JwtUtils jwtUtils;

    @Override
    public UserResponseDto getOneUser(Long id) {

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

        if(foundUser.isDeleted()) {
            // @Todo customize Exception
            throw new RuntimeException("Failed: Already deleted user");
        }

        foundUser.setDeleted(true);

        return UserResponseDto.from(foundUser);
    }


}
