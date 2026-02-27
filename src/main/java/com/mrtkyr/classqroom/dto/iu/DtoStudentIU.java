package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.enums.GenderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudentIU {

    @NotNull(message = "First Name cannot be null!")
    @Size(min = 2, max = 32, message = "First Name must be between 2 and 32 characters!")
    private String firstName;

    @NotNull(message = "Last Name cannot be null!")
    @Size(min = 2, max = 32, message = "Last Name must be between 2 and 32 characters!")
    private String lastName;

    @NotNull(message = "Gender cannot be null!")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private GenderType gender;

    @NotNull(message = "Department Id cannot be null!")
    private Department department;

    @NotNull(message = "Student Number cannot be null!")
    private String studentNumber;

    @NotNull(message = "Years of Study cannot be null!")
    @Min(value = 0, message = "Years of Study cannot be negative!")
    @Max(value = 6, message = "Years of Study cannot be upper than 6!")
    private Integer yearOfStudy;

    @NotNull(message = "GPA cannot be null!")
    @DecimalMin(value = "0.00", message = "GPA cannot be negative!")
    @DecimalMax(value = "4.00", message = "GPA cannot be upper than 4!")
    private BigDecimal gpa;

    @NotNull(message = "Cumulative GPA cannot be null!")
    @DecimalMin(value = "0.00", message = "Cumulative GPA cannot be negative!")
    @DecimalMax(value = "4.00", message = "Cumulative GPA cannot be upper than 4!")
    private BigDecimal cgpa;

    @NotNull(message = "In Course variable cannot be null!")
    private boolean inCourse;

    @NotNull(message = "Active variable cannot be null!")
    private boolean active;

    @NotNull(message = "In Campus variable cannot be null!")
    private boolean inCampus;

}
