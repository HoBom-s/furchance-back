package com.hobom.furchance.user.controller;

import com.hobom.furchance.auth.util.JwtUtils;
import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.dto.UserUpdateRequestDto;
import com.hobom.furchance.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Url.User.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> getOneUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: get one user", userService.getOneUser(id)));
    }

    @PatchMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> updateOneUser(@RequestHeader("Authorization") String authHeader, @PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {

        // @Todo Authorization Header JwtUtils
        String accessToken = jwtUtils.extractAccessToken(authHeader);

        Long userId = jwtUtils.extractUserId(accessToken);

        if(userId != id) {
            throw new RuntimeException("Auth userId does not match the requested userId");
        }

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one user", userService.updateOneUser(id, userUpdateRequestDto)));
    }

    @DeleteMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> removeOneUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one user", userService.removeOneUser(id)));
    }
}
