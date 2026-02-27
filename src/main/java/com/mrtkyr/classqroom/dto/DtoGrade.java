package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.GradeId;
import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.enums.AssessmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoGrade {
    private GradeId id;
    private Student student;
    private Course course;
    private AssessmentType assessmentType;
    private int grade;
    private LocalDateTime createdAt;
}
