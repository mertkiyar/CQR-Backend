package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.entity.StudentCourseId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudentCourse {
    private StudentCourseId id;
    private Student student;
    private Course course;
    private LocalDateTime enrolledAt;
    private boolean active;
}
