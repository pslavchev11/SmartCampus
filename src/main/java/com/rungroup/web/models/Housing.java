package com.rungroup.web.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "housing")
public class Housing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String buildingName;
    private String roomNumber;
    private Integer pricePerSemester;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = true)
    private UserEntity student;
}
