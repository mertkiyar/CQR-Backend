package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.StudentCourse;
import com.mrtkyr.classqroom.entity.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
    boolean existsByStudent_UserIdAndCourse_CourseIdAndActiveTrue(UUID studentId, UUID courseId);
}
