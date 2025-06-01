package com.rungroup.web.service.impl;


import com.rungroup.web.dto.HousingDto;
import com.rungroup.web.mapper.HousingMapper;
import com.rungroup.web.models.Housing;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.HousingRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.service.HousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HousingImplService implements HousingService {

    private final HousingRepository housingRepository;
    private final UserRepository userRepository;

    @Autowired
    public HousingImplService(HousingRepository housingRepository, UserRepository userRepository) {
        this.housingRepository = housingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<HousingDto> findAllHousing() {
        return housingRepository.findAllByOrderByBuildingNameAscRoomNumberAsc()
                .stream()
                .map(HousingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HousingDto> findById(Long id) {
        return housingRepository.findById(id)
                .map(HousingMapper::toDto);
    }

    @Override
    public Optional<HousingDto> findByStudentId(Long studentId) {
        return housingRepository.findByStudentId(studentId)
                .stream()
                .findFirst()
                .map(HousingMapper::toDto);
    }

    @Override
    public HousingDto save(HousingDto housingDto) {
        Housing housing = HousingMapper.toEntity(housingDto);

        if (housingDto.getStudentId() != null) {
            UserEntity student = userRepository.findById(housingDto.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + housingDto.getStudentId()));
            housing.setStudent(student);
        } else {
            housing.setStudent(null);
        }

        Housing savedHousing = housingRepository.save(housing);

        return HousingMapper.toDto(savedHousing);
    }

    @Override
    public void deleteById(Long id) {
        housingRepository.deleteById(id);
    }
}
