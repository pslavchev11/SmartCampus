package com.rungroup.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationDto {
    public interface OnCreate {}

    private Long id;
    @NotNull
    private Integer uniqueNumber;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty(groups = OnCreate.class)
    private String password;
    @NotEmpty
    private String role;
}
