package com.hobom.furchance.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

}
