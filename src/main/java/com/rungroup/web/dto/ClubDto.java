package com.rungroup.web.dto;

import com.rungroup.web.models.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder

public class ClubDto {

    private  Long id;
    @NotEmpty(message = "The club title should not be empty!")
    private String title;
    @NotEmpty(message = "The photo link should not be empty!")
    private String photoUrl;
    @NotEmpty(message = "The content should not be empty!")
    private String content;
    private UserEntity createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDto> events;
}
