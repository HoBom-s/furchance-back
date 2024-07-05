package com.hobom.furchance.auth.controller;

import com.hobom.furchance.auth.dto.SignUpRequestDto;
import com.hobom.furchance.auth.service.AuthService;
import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.auth.dto.UserLogInRequestDto;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.mchange.util.AlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Url.User.BASE_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(Url.User.SIGNUP)
    public ResponseEntity<ApiResponse<UserResponseDto>> signUp(@RequestBody @Valid SignUpRequestDto userCreateRequestDto) throws AlreadyExistsException {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: sign up one user", authService.signUp(userCreateRequestDto)));
    }

    @PostMapping(Url.User.LOGIN)
    public ResponseEntity<ApiResponse<UserResponseDto>> logIn(@RequestBody @Valid UserLogInRequestDto userLogInRequestDto, HttpServletResponse httpServletResponse) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: log in", authService.logIn(userLogInRequestDto, httpServletResponse)));
    }
}
