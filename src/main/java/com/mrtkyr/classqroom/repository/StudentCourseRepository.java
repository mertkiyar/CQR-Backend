package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.StudentCourse;
import com.mrtkyr.classqroom.entity.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
}
