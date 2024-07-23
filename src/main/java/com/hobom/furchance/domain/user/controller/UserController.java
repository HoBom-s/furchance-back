package com.hobom.furchance.domain.user.controller;

import com.hobom.furchance.domain.user.dto.UserResponseDto;
import com.hobom.furchance.domain.user.service.UserService;
import com.hobom.furchance.dto.ApiResponse;
import com.hobom.furchance.url.Url;
import com.hobom.furchance.domain.user.dto.UserUpdateRequestDto;
import com.hobom.furchance.util.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> getOneUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: get one user", userService.getOneUserById(id)));
    }

    @PatchMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> updateOneUser(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto, HttpServletRequest request) {

        Long verifiedUserId = CommonUtils.getVerifiedUserId(request);
        CommonUtils.validateUser(verifiedUserId, id);

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one user", userService.updateOneUser(id, userUpdateRequestDto)));
    }

    @DeleteMapping(Url.ID_PARAM)
    public ResponseEntity<ApiResponse<UserResponseDto>> removeOneUser(@PathVariable("id") Long id, HttpServletRequest request) {

        Long verifiedUserId = CommonUtils.getVerifiedUserId(request);
        CommonUtils.validateUser(verifiedUserId, id);

        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "Success: update one user", userService.removeOneUser(id)));
    }
}