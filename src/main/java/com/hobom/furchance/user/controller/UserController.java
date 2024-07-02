package com.hobom.furchance.user.controller;

import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.user.dto.UserCreateRequestDto;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.dto.UserUpdateRequestDto;
import com.hobom.furchance.user.service.UserService;
import com.mchange.util.AlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Url.User.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createOneUser(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) throws AlreadyExistsException {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: create one user", userService.createOneUser(userCreateRequestDto)));
    }

    @GetMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> getOneUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: get one user", userService.getOneUser(id)));
    }

    @PatchMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> updateOneUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one user", userService.updateOneUser(id, userUpdateRequestDto)));
    }

    @DeleteMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> removeOneUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one user", userService.removeOneUser(id)));
    }
}
