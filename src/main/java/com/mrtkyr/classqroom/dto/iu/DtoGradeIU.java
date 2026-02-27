package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.enums.AssessmentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoGradeIU {

    @NotNull(message = "Student cannot be null!")
    private Student student;

    @NotNull(message = "Course cannot be null!")
    private Course course;

    @NotNull(message = "Assessment Type cannot be null!")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AssessmentType assessmentType;

    @NotNull(message = "Grade cannot be null!")
    @Min(value = 0, message = "Grade cannot be negative!")
    @Max(value = 100, message = "Grade cannot be upper than 100!")
    private int grade;
}
