package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.AttendanceRecord;
import com.mrtkyr.classqroom.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, UUID> {
    List<AttendanceRecord> findByStudent_UserIdOrderByAttendAtDesc(UUID studentId);
    List<AttendanceRecord> findByAttendanceSession_Attendance_CourseInOrderByAttendAtDesc(Collection<Course> courses);
}
