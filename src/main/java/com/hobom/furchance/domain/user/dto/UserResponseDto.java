package com.hobom.furchance.domain.user.dto;

import com.hobom.furchance.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;

    private String nickname;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getId(), user.getNickname());
    }
}
