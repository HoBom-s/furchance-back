package com.hobom.furchance.domain.auth.service;

import com.hobom.furchance.domain.auth.dto.SignUpRequestDto;
import com.hobom.furchance.domain.auth.dto.UserLogInRequestDto;
import com.hobom.furchance.domain.auth.dto.UserLoginResponseDto;
import com.hobom.furchance.domain.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService{

    UserResponseDto signUp(SignUpRequestDto signUpRequestDto);

    UserLoginResponseDto logIn(UserLogInRequestDto userLogInRequestDto, HttpServletResponse httpServletResponse);
}
