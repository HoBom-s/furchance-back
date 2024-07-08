package com.hobom.furchance.user.service;

import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.dto.UserUpdateRequestDto;

public interface UserService {

    UserResponseDto getOneUser(Long id);

    UserResponseDto updateOneUser(Long id, UserUpdateRequestDto userUpdateRequestDto);

    UserResponseDto removeOneUser(Long id);
}
