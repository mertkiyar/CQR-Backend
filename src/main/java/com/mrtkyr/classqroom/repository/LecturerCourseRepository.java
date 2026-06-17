package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.LecturerCourse;
import com.mrtkyr.classqroom.entity.LecturerCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LecturerCourseRepository extends JpaRepository<LecturerCourse, LecturerCourseId> {

    @Query("SELECT lc.course FROM LecturerCourse lc WHERE lc.lecturer.userId = :lecturerId AND lc.active = true")
    List<Course> findActiveCoursesByLecturerId(@Param("lecturerId") UUID lecturerId);
}
