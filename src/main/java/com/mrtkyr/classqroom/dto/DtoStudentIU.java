package com.mrtkyr.classqroom.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudentIU {

    @NotNull(message = "Years of Study cannot be null!")
    @Min(value = 0, message = "Years of Study cannot be negative!")
    @Max(value = 6, message = "Years of Study cannot be upper than 6!")
    private int yearOfStudy;

    @NotNull(message = "GPA cannot be null!")
    @DecimalMin(value = "0.00", message = "GPA cannot be negative!")
    @DecimalMax(value = "4.00", message = "GPA cannot be upper than 4!")
    private BigDecimal gpa;

    @NotNull(message = "GPA cannot be null!")
    @DecimalMin(value = "0.00", message = "Cumulative GPA cannot be negative!")
    @DecimalMax(value = "4.00", message = "Cumulative GPA cannot be upper than 4!")
    private BigDecimal cgpa;

    private boolean active;
    private boolean inCampus;

}
