package com.hobom.furchance.domain.auth.controller;

import com.hobom.furchance.domain.auth.dto.SignUpRequestDto;
import com.hobom.furchance.domain.auth.dto.UserLoginResponseDto;
import com.hobom.furchance.domain.auth.service.AuthService;
import com.hobom.furchance.domain.auth.dto.UserLogInRequestDto;
import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.domain.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Url.Auth.BASE_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(Url.Auth.SIGNUP)
    public ResponseEntity<ApiResponse<UserResponseDto>> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: sign up one user", authService.signUp(signUpRequestDto)));
    }

    @PostMapping(Url.Auth.LOGIN)
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> logIn(
            @RequestBody @Valid UserLogInRequestDto userLogInRequestDto,
            HttpServletResponse httpServletResponse
    ) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: log in", authService.logIn(userLogInRequestDto, httpServletResponse)));
    }
}
