package com.hobom.furchance.auth.service;

import com.hobom.furchance.auth.dto.SignUpRequestDto;
import com.hobom.furchance.auth.dto.UserLogInRequestDto;
import com.hobom.furchance.auth.dto.UserLoginResponseDto;
import com.hobom.furchance.auth.util.JwtUtils;
import com.hobom.furchance.auth.util.PasswordUtils;
import com.hobom.furchance.config.RedisConfig;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.repository.UserRepository;
import com.hobom.furchance.user.service.UserValidationService;
import com.mchange.util.AlreadyExistsException;
import io.lettuce.core.api.async.RedisAsyncCommands;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final UserValidationService userValidationService;

    private final PasswordUtils passwordUtils;

    private final JwtUtils jwtUtils;

    private final RedisConfig redisConfig;

    private final RedisService redisService;

    @Override
    public UserResponseDto signUp(SignUpRequestDto userCreateRequestDto) throws AlreadyExistsException {

        userValidationService.validateNickname(userCreateRequestDto.getNickname());

        String encodedPassword = passwordUtils.encodePassword(userCreateRequestDto.getPassword());

        User createdUser = userRepository.save(User.of(userCreateRequestDto.getNickname(), encodedPassword));

        return UserResponseDto.from(createdUser);
    }

    @Override
    public UserLoginResponseDto logIn(UserLogInRequestDto userLogInRequestDto, HttpServletResponse httpServletResponse) {

        User foundUser = userValidationService.findOneUserByNickname(userLogInRequestDto.getNickname());

        boolean isPasswordValid = passwordUtils.validatePassword(foundUser, userLogInRequestDto.getPassword());

        if(!isPasswordValid) {
            throw new BadCredentialsException("Invalid password");
        }

        String accessToken = jwtUtils.generateToken(foundUser, "access");
        jwtUtils.setAccessTokenToCookie(httpServletResponse, accessToken);

        String refreshToken = jwtUtils.generateToken(foundUser, "refresh");
        redisService.setRefreshToken(foundUser, refreshToken);

        return UserLoginResponseDto.from(foundUser, accessToken);
    }
}
