package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Attendance;
import com.mrtkyr.classqroom.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    List<Attendance> findAllByActiveTrue();
    Optional<Attendance> findByNfcPath(UUID nfcPath);
    Optional<Attendance> findByCourseAndActiveTrue(Course course);
    List<Attendance> findByActiveTrueAndExpiresAtBefore(LocalDateTime dateTime);
}
