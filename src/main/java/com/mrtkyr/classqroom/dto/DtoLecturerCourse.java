package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.Lecturer;
import com.mrtkyr.classqroom.entity.LecturerCourseId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoLecturerCourse {
    private LecturerCourseId id;
    private Lecturer lecturer;
    private Course course;
    private LocalDateTime enrolledAt;
    private boolean active;
}
