package com.mrtkyr.classqroom.dto.iu;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoCourseIdReference {
    @NotNull(message = "Course ID cannot be null!")
    private UUID courseId;
}
