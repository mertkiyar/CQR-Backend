package com.mrtkyr.classqroom.starter;

import com.mrtkyr.classqroom.dto.iu.DtoAttendanceRecordIU;
import com.mrtkyr.classqroom.entity.Attendance;
import com.mrtkyr.classqroom.entity.AttendanceRecord;
import com.mrtkyr.classqroom.entity.AttendanceSession;
import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.enums.AttendanceType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.repository.AttendanceRecordRepository;
import com.mrtkyr.classqroom.repository.AttendanceSessionRepository;
import com.mrtkyr.classqroom.repository.StudentCourseRepository;
import com.mrtkyr.classqroom.repository.StudentRepository;
import com.mrtkyr.classqroom.service.impl.AttendanceRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AttendanceRecordRulesTests {
    private final UUID studentId = UUID.randomUUID();
    private final UUID courseId = UUID.randomUUID();
    private final UUID attendanceId = UUID.randomUUID();
    private final UUID sessionId = UUID.randomUUID();

    private AttendanceRecordServiceImpl service;
    private StudentCourseRepository studentCourses;
    private AttendanceRecordRepository records;
    private DtoAttendanceRecordIU request;

    @BeforeEach
    void setUp() {
        service = new AttendanceRecordServiceImpl();
        StudentRepository students = mock(StudentRepository.class);
        AttendanceSessionRepository sessions = mock(AttendanceSessionRepository.class);
        studentCourses = mock(StudentCourseRepository.class);
        records = mock(AttendanceRecordRepository.class);
        ReflectionTestUtils.setField(service, "studentRepository", students);
        ReflectionTestUtils.setField(service, "attendanceSessionRepository", sessions);
        ReflectionTestUtils.setField(service, "studentCourseRepository", studentCourses);
        ReflectionTestUtils.setField(service, "attendanceRecordRepository", records);

        Student student = new Student();
        student.setUserId(studentId);
        Course course = new Course();
        course.setCourseId(courseId);
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(attendanceId);
        attendance.setCourse(course);
        AttendanceSession session = new AttendanceSession();
        session.setAttendanceSessionId(sessionId);
        session.setAttendance(attendance);
        when(students.findById(studentId)).thenReturn(Optional.of(student));
        when(sessions.findById(sessionId)).thenReturn(Optional.of(session));

        request = new DtoAttendanceRecordIU();
        request.setStudentId(studentId);
        request.setAttendanceSessionId(sessionId);
        request.setAttendanceType(AttendanceType.QR_CODE);
        request.setDeviceId(UUID.randomUUID());
        request.setClientIp("127.0.0.1");
        request.setAttendAt(LocalDateTime.now());
    }

    @Test
    void studentWithoutCourseEnrollmentCannotAttend() {
        BaseException error = assertThrows(BaseException.class, () -> service.saveAttendanceRecord(request));

        assertEquals("4001", error.getMessageType().getCode());
        verify(records, never()).save(any());
    }

    @Test
    void duplicateAttendanceIsCheckedAcrossRotatingSessions() {
        when(studentCourses.existsByStudent_UserIdAndCourse_CourseIdAndActiveTrue(studentId, courseId))
                .thenReturn(true);
        when(records.existsByStudent_UserIdAndAttendanceSession_Attendance_AttendanceId(studentId, attendanceId))
                .thenReturn(true);

        BaseException error = assertThrows(BaseException.class, () -> service.saveAttendanceRecord(request));

        assertEquals("1002", error.getMessageType().getCode());
        verify(records, never()).save(any());
    }

    @Test
    void enrolledStudentCanRecordFirstAttendance() {
        when(studentCourses.existsByStudent_UserIdAndCourse_CourseIdAndActiveTrue(studentId, courseId))
                .thenReturn(true);
        when(records.save(any(AttendanceRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveAttendanceRecord(request);

        verify(records).save(any(AttendanceRecord.class));
    }
}
