package com.hobom.furchance.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

}
