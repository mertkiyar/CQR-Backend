package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.Student;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudentCourseIU {

    @NotNull(message = "Student cannot be null!")
    private Student student;

    @NotNull(message = "Course cannot be null!")
    private Course course;

    @NotNull(message = "Active variable cannot be null!")
    private boolean active;
}
