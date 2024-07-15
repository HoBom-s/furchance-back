package com.hobom.furchance.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogInRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

}
