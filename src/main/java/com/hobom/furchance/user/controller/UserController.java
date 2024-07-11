package com.hobom.furchance.user.controller;

import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.dto.UserUpdateRequestDto;
import com.hobom.furchance.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(Url.User.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> getOneUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: get one user", userService.getOneUser(id)));
    }

    @PatchMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> updateOneUser(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto, HttpServletRequest request) {

        Long verifiedUserId = (Long) request.getAttribute("verifiedUserId");

        if (!Objects.equals(verifiedUserId, id)) {
            throw new RuntimeException("Failed: only the account owner is permitted to update");
        }

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one user", userService.updateOneUser(id, userUpdateRequestDto)));
    }

    @DeleteMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> removeOneUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one user", userService.removeOneUser(id)));
    }
}
