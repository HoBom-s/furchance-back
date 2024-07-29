package com.hobom.furchance.domain.user.service;

import com.hobom.furchance.domain.auth.util.PasswordUtils;
import com.hobom.furchance.domain.user.dto.UserResponseDto;
import com.hobom.furchance.domain.user.dto.UserUpdateRequestDto;
import com.hobom.furchance.domain.user.entity.User;
import com.hobom.furchance.domain.user.repository.UserRepository;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordUtils passwordUtils;

    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("해당 ID 값의 유저 정보를 가져온다.")
    void 유저_정보_가져오기() {

        Long userId = 1L;
        String nickname = "TestUser";
        String password = "TestPassword";

        User user = new User();
        user.setId(userId);
        user.setNickname(nickname);
        user.setPassword(password);

        when(userValidationService.findOneUserById(userId)).thenReturn(user);

        UserResponseDto userResponseDto = userService.getOneUserById(userId);

        assertThat(userResponseDto.getId()).isEqualTo(1L);
        assertThat(userResponseDto.getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("해당 ID 값의 유저가 수정되어야 한다.")
    void 유저_수정하기() {

        Long userId = 1L;
        String oldNickname = "OldNickname";
        String oldPassword = "OldPassword";

        User user = new User();
        user.setId(userId);
        user.setNickname(oldNickname);
        user.setPassword(oldPassword);

        String updatedNickname = "UpdatedNickname";
        String updatedPassword = "UpdatedPassword";

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
        userUpdateRequestDto.setNickname(updatedNickname);
        userUpdateRequestDto.setPassword(updatedPassword);

        when(userValidationService.findOneUserById(userId)).thenReturn(user);
        when(passwordUtils.encodePassword(updatedPassword)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto userResponseDto = userService.updateOneUser(userId, userUpdateRequestDto);

        assertThat(userResponseDto.getId()).isEqualTo(1L);
        assertThat(userResponseDto.getNickname()).isEqualTo(updatedNickname);
    }

    @Test
    @DisplayName("해당 ID 값의 유저가 삭제되어야 한다.")
    void 유저_삭제하기() {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setDeleted(false);

        when(userValidationService.findOneUserById(userId)).thenReturn(user);

        userService.removeOneUser(userId);

        assertThat(user.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("해당 ID값의 유저가 이미 삭제되었을 시 에러가 발생한다.")
    void 이미_유저가_삭제된_경우() {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setDeleted(true);

        when(userValidationService.findOneUserById(userId)).thenReturn(user);

        CustomException customException = assertThrows(CustomException.class, () -> {
            userService.removeOneUser(userId);
        });

        assertThat(customException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(customException.getMessage()).isEqualTo(ErrorMessage.ALREADY_DELETED + userId);
    }
}