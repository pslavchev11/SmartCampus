package com.rungroup.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private Integer uniqueNumber;
    private String username;
    private String email;
}
