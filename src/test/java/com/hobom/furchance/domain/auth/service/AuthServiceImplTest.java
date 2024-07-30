package com.hobom.furchance.domain.auth.service;

import com.hobom.furchance.domain.auth.constant.AuthConstant;
import com.hobom.furchance.domain.auth.dto.SignUpRequestDto;
import com.hobom.furchance.domain.auth.dto.UserLogInRequestDto;
import com.hobom.furchance.domain.auth.dto.UserLoginResponseDto;
import com.hobom.furchance.domain.auth.util.JwtUtils;
import com.hobom.furchance.domain.auth.util.PasswordUtils;
import com.hobom.furchance.domain.user.dto.UserResponseDto;
import com.hobom.furchance.domain.user.entity.User;
import com.hobom.furchance.domain.user.repository.UserRepository;
import com.hobom.furchance.domain.user.service.UserValidationService;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private PasswordUtils passwordUtils;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private RedisService redisService;

    @Mock
    private HttpServletResponse httpServletResponse;


    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("유저 회원가입을 진행합니다.")
    void 회원가입() {

        Long userId = 1L;
        String nickname = "testUser";
        String password = "testPassword";
        String encodedPassword = "encodedPassword";

        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setNickname(nickname);
        signUpRequestDto.setPassword(password);

        User user = new User();
        user.setId(userId);
        user.setNickname(nickname);
        user.setPassword(encodedPassword);

        when(passwordUtils.encodePassword(signUpRequestDto.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto userResponseDto = authService.signUp(signUpRequestDto);

        assertThat(userResponseDto.getId()).isEqualTo(1L);
        assertThat(userResponseDto.getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("등록된 유저는 반드시 로그인 되어야 한다.")
    void 로그인_성공() {

        Long userId = 1L;
        String nickname = "testNickname";
        String password = "testPassword";

        UserLogInRequestDto userLogInRequestDto = new UserLogInRequestDto();
        userLogInRequestDto.setNickname(nickname);
        userLogInRequestDto.setPassword(password);

        User user = new User();
        user.setId(userId);
        user.setNickname(nickname);
        user.setPassword(password);

        when(userValidationService.findOneUserByNickname(userLogInRequestDto.getNickname())).thenReturn(user);
        when(passwordUtils.validatePassword(user, userLogInRequestDto.getPassword())).thenReturn(true);
        when(jwtUtils.generateToken(user, AuthConstant.ACCESS)).thenReturn("accessToken");
        when(jwtUtils.generateToken(user, AuthConstant.REFRESH)).thenReturn("refreshToken");

        UserLoginResponseDto userLoginResponseDto = authService.logIn(userLogInRequestDto, httpServletResponse);

        assertThat(userLoginResponseDto.getNickname()).isEqualTo(nickname);
        assertThat(userLoginResponseDto.getAccessToken()).isEqualTo("accessToken");
    }

    @Test
    @DisplayName("로그인 시 패스워드가 틀린 경우 에러가 발생한다.")
    void 로그인_실패_패스워드_오류() {

        Long userId = 1L;
        String nickname = "testNickname";
        String password = "testPassword";

        UserLogInRequestDto userLogInRequestDto = new UserLogInRequestDto();
        userLogInRequestDto.setNickname(nickname);
        userLogInRequestDto.setPassword(password);

        User user = new User();
        user.setId(userId);
        user.setNickname(nickname);
        user.setPassword(password);

        when(userValidationService.findOneUserByNickname(userLogInRequestDto.getNickname())).thenReturn(user);
        when(passwordUtils.validatePassword(user, userLogInRequestDto.getPassword())).thenReturn(false);

        CustomException customException = assertThrows(CustomException.class, () -> {
            authService.logIn(userLogInRequestDto, httpServletResponse);
        });

        assertThat(customException.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(customException.getMessage()).isEqualTo(ErrorMessage.WRONG_PASSWORD);
    }
}:!