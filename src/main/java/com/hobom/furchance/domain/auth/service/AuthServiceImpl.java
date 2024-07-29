package com.hobom.furchance.domain.auth.service;

import com.hobom.furchance.domain.auth.constant.AuthConstant;
import com.hobom.furchance.domain.auth.dto.SignUpRequestDto;
import com.hobom.furchance.domain.auth.dto.UserLogInRequestDto;
import com.hobom.furchance.domain.auth.dto.UserLoginResponseDto;
import com.hobom.furchance.domain.auth.util.JwtUtils;
import com.hobom.furchance.domain.auth.util.PasswordUtils;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import com.hobom.furchance.domain.user.dto.UserResponseDto;
import com.hobom.furchance.domain.user.entity.User;
import com.hobom.furchance.domain.user.repository.UserRepository;
import com.hobom.furchance.domain.user.service.UserValidationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final UserValidationService userValidationService;

    private final PasswordUtils passwordUtils;

    private final JwtUtils jwtUtils;

    private final RedisService redisService;

    @Override
    @Transactional
    public UserResponseDto signUp(SignUpRequestDto userCreateRequestDto) {

        userValidationService.validateNickname(userCreateRequestDto.getNickname());

        String encodedPassword = passwordUtils.encodePassword(userCreateRequestDto.getPassword());

        User createdUser = userRepository.save(User.of(userCreateRequestDto.getNickname(), encodedPassword));

        return UserResponseDto.from(createdUser);
    }

    @Override
    @Transactional
    public UserLoginResponseDto logIn(UserLogInRequestDto userLogInRequestDto, HttpServletResponse httpServletResponse) {

        User foundUser = userValidationService.findOneUserByNickname(userLogInRequestDto.getNickname());

        boolean isPasswordValid = passwordUtils.validatePassword(foundUser, userLogInRequestDto.getPassword());

        if(!isPasswordValid) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorMessage.WRONG_PASSWORD);
        }

        String accessToken = jwtUtils.generateToken(foundUser, AuthConstant.ACCESS);
        jwtUtils.setAccessTokenToCookie(httpServletResponse, accessToken);

        String refreshToken = jwtUtils.generateToken(foundUser, AuthConstant.REFRESH);
        redisService.setRefreshToken(foundUser, refreshToken);

        return UserLoginResponseDto.from(foundUser, accessToken);
    }
}
