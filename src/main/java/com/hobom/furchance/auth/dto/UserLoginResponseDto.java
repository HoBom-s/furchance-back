package com.hobom.furchance.auth.dto;

import com.hobom.furchance.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserLoginResponseDto {

    private final Long id;

    private final String nickname;

    private final String accessToken;

    public static UserLoginResponseDto from(User user, String accessToken) {
        return new UserLoginResponseDto(user.getId(), user.getNickname(), accessToken);
    }
}
