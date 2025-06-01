package com.rungroup.web.mapper;

import com.rungroup.web.dto.HousingDto;
import com.rungroup.web.models.Housing;

public class HousingMapper {

    public static HousingDto toDto(Housing housing) {
        if (housing == null) {
            return null;
        }
        HousingDto dto = new HousingDto();
        dto.setId(housing.getId());
        dto.setBuildingName(housing.getBuildingName());
        dto.setRoomNumber(housing.getRoomNumber());
        dto.setPricePerSemester(housing.getPricePerSemester());
        if (housing.getStudent() != null) {
            dto.setStudentId(housing.getStudent().getId());
            dto.setStudentUsername(housing.getStudent().getUsername());
        }
        return dto;
    }

    public static Housing toEntity(HousingDto dto) {
        if (dto == null) {
            return null;
        }
        Housing housing = new Housing();
        housing.setId(dto.getId());
        housing.setBuildingName(dto.getBuildingName());
        housing.setRoomNumber(dto.getRoomNumber());
        housing.setPricePerSemester(dto.getPricePerSemester());

        return housing;
    }
}
