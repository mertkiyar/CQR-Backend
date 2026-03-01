package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.Lecturer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLecturerCourseIU {

    @NotNull(message = "Lecturer cannot be null!")
    private Lecturer lecturer;

    @NotNull(message = "Course cannot be null!")
    private Course course;

    @NotNull(message = "Active variable cannot be null!")
    private boolean active;
}
