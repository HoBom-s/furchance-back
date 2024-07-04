package com.hobom.furchance.auth.service;

import com.hobom.furchance.auth.dto.SignUpRequestDto;
import com.hobom.furchance.auth.dto.UserLogInRequestDto;
import com.hobom.furchance.auth.util.JwtUtils;
import com.hobom.furchance.auth.util.PasswordUtils;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.repository.UserRepository;
import com.hobom.furchance.user.service.UserValidationService;
import com.mchange.util.AlreadyExistsException;
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

    @Override
    public UserResponseDto signUp(SignUpRequestDto userCreateRequestDto) throws AlreadyExistsException {

        userValidationService.validateNickname(userCreateRequestDto.getNickname());

        String encodedPassword = passwordUtils.encodePassword(userCreateRequestDto.getPassword());

        User createdUser = userRepository.save(User.of(userCreateRequestDto.getNickname(), encodedPassword));

        return UserResponseDto.from(createdUser);
    }

    @Override
    public UserResponseDto logIn(UserLogInRequestDto userLogInRequestDto, HttpServletResponse httpServletResponse) {

        User foundUser = userValidationService.findOneUserByNickname(userLogInRequestDto.getNickname());

        boolean isPasswordValid = passwordUtils.validatePassword(foundUser, userLogInRequestDto.getPassword());

        if(!isPasswordValid) {
            throw new BadCredentialsException("Invalid password");
        }

        // @Todo 헤더 쿠키 세팅
        String accessToken = jwtUtils.generateAccessToken(foundUser);

        jwtUtils.setTokenToCookie(httpServletResponse, accessToken);

        return UserResponseDto.from(foundUser);
    }
}
