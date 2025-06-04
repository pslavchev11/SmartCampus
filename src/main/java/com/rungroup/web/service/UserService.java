package com.rungroup.web.service;

import com.rungroup.web.dto.RegistrationDto;
import com.rungroup.web.dto.UserDto;
import com.rungroup.web.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    UserEntity findByUniqueNumber(Integer uniqueNumber);

    Optional<UserEntity> findById(Long studentId);

    List<UserDto> findByRole(String roleName);
}
