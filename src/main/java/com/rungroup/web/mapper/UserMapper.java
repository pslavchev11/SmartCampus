package com.rungroup.web.mapper;

import com.rungroup.web.dto.UserDto;
import com.rungroup.web.models.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto mapToUserDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
