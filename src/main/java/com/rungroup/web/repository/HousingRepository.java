package com.rungroup.web.repository;

import com.rungroup.web.models.Housing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HousingRepository extends JpaRepository<Housing, Long> {

    List<Housing> findAllByOrderByBuildingNameAscRoomNumberAsc();

    List<Housing> findByStudentId(Long studentId);
}
