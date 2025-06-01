package com.rungroup.web.dto;

import lombok.Data;

@Data
public class HousingDto {
    private Long id;
    private String buildingName;
    private String roomNumber;
    private Integer pricePerSemester;
    private Long studentId;
    private String studentUsername;
}
