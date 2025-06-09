package com.rungroup.web.mapper;

import com.rungroup.web.dto.RegistrationDto;
import com.rungroup.web.models.UserEntity;

public class RegistrationMapper {
    public static RegistrationDto fromUserEntity(UserEntity e) {
        RegistrationDto d = new RegistrationDto();
        d.setId(e.getId());
        d.setUniqueNumber(e.getUniqueNumber());
        d.setUsername(e.getUsername());
        d.setEmail(e.getEmail());
        d.setPassword(e.getPassword());
        d.setRole(e.getRoles().get(0).getName());
        return d;
    }
}
