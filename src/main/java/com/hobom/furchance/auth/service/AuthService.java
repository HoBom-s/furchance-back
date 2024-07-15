package com.hobom.furchance.auth.service;

import com.hobom.furchance.auth.dto.SignUpRequestDto;
import com.hobom.furchance.auth.dto.UserLogInRequestDto;
import com.hobom.furchance.auth.dto.UserLoginResponseDto;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.mchange.util.AlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService{

    UserResponseDto signUp(SignUpRequestDto signUpRequestDto);

    UserLoginResponseDto logIn(UserLogInRequestDto userLogInRequestDto, HttpServletResponse httpServletResponse);
}
