package com.rungroup.web.service;

import com.rungroup.web.dto.HousingDto;

import java.util.List;
import java.util.Optional;

public interface HousingService {
    List<HousingDto> findAllHousing();
    Optional<HousingDto> findById(Long id);
    Optional<HousingDto> findByStudentId(Long studentId);
    HousingDto save(HousingDto housingDto);
    void deleteById(Long id);
}
