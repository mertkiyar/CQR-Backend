package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.LecturerCourse;
import com.mrtkyr.classqroom.entity.LecturerCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerCourseRepository extends JpaRepository<LecturerCourse, LecturerCourseId> {
}
