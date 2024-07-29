package com.hobom.furchance.domain.user.service;

import com.hobom.furchance.domain.user.dto.UserResponseDto;
import com.hobom.furchance.domain.user.dto.UserUpdateRequestDto;

public interface UserService {

    UserResponseDto getOneUserById(Long id);

    UserResponseDto updateOneUser(Long id, UserUpdateRequestDto userUpdateRequestDto);

    UserResponseDto removeOneUser(Long id);
}
