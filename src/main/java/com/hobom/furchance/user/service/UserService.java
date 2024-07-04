package com.hobom.furchance.user.service;

import com.hobom.furchance.user.dto.UserCreateRequestDto;
import com.hobom.furchance.user.dto.UserResponseDto;
import com.hobom.furchance.user.dto.UserUpdateRequestDto;
import com.mchange.util.AlreadyExistsException;

public interface UserService {

    public UserResponseDto createOneUser(UserCreateRequestDto userCreateRequestDto) throws AlreadyExistsException;

    public UserResponseDto getOneUser(Long id);

    public UserResponseDto updateOneUser(Long id, UserUpdateRequestDto userUpdateRequestDto);

    public UserResponseDto removeOneUser(Long id);
}
