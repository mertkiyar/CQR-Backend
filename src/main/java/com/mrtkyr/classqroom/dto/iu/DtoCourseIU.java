package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.entity.Language;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoCourseIU {

    @NotNull(message = "Course Name cannot be null!")
    @Size(max = 50, message = "Course Name cannot be upper than 50 chars!")
    private String courseName;

    @NotNull(message = "Course Code cannot be null!")
    @Size(min = 5, message = "Course Code cannot be lower than 5 chars!")
    @Size(max = 7, message = "Course Code cannot be upper than 7 chars!")
    private String courseCode;

    @NotNull(message = "Ects cannot be null!")
    @DecimalMin(value = "0", message = "Ects cannot be negative!")
    private BigDecimal courseEcts;

    @NotNull(message = "Credit cannot be null!")
    @DecimalMin(value = "0", message = "Credit cannot be negative!")
    private BigDecimal courseCredit;

    @NotNull(message = "Hours of theoretical course cannot be null!")
    @Min(value = 0, message = "Hours of theoretical course cannot be lower than 0!")
    @Max(value = 10, message = "Hours of theoretical course cannot be upper than 10!")
    private Short hoursTheoretical;

    @NotNull(message = "Hours of practical course cannot be null!")
    @Min(value = 0, message = "Hours of practical course cannot be lower than 0!")
    @Max(value = 10, message = "Hours of practical course cannot be upper than 10!")
    private Short hoursPractical;

    @NotNull(message = "Minimum Attendance Percent cannot be null!")
    @Min(value = 0, message = "Minimum Attendance Percent cannot be lower than 0%!")
    @Max(value = 100, message = "Minimum Attendance Percent cannot be upper than 100%!")
    private Integer minAttendancePercent;

    @NotNull(message = "Language cannot be null!")
    private Language language;

    @NotNull(message = "Online variable cannot be null!")
    private boolean online;

    @NotNull(message = "Elective variable cannot be null!")
    private boolean elective;

    @NotNull(message = "Department cannot be null!")
    private Department department;
}
