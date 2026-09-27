package com.mrtkyr.classqroom.starter;

import com.mrtkyr.classqroom.dto.iu.DtoStudentIU;
import com.mrtkyr.classqroom.entity.AttendanceRecord;
import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.enums.GenderType;
import com.mrtkyr.classqroom.repository.AttendanceRecordRepository;
import com.mrtkyr.classqroom.repository.DepartmentRepository;
import com.mrtkyr.classqroom.repository.StudentRepository;
import com.mrtkyr.classqroom.service.impl.AttendanceRecordServiceImpl;
import com.mrtkyr.classqroom.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminEditTests {
    @Test
    void studentEditPersistsIdentityAndAcademicFields() {
        StudentServiceImpl service = new StudentServiceImpl();
        StudentRepository students = mock(StudentRepository.class);
        DepartmentRepository departments = mock(DepartmentRepository.class);
        ReflectionTestUtils.setField(service, "studentRepository", students);
        ReflectionTestUtils.setField(service, "departmentRepository", departments);
        UUID id = UUID.randomUUID();
        Student student = new Student();
        student.setUserId(id);
        Department department = new Department();
        department.setId((short) 2);
        when(students.findById(id)).thenReturn(Optional.of(student));
        when(departments.findById((short) 2)).thenReturn(Optional.of(department));
        when(students.save(student)).thenReturn(student);

        DtoStudentIU edit = new DtoStudentIU();
        edit.setFirstName("Ada");
        edit.setLastName("Demir");
        edit.setGender(GenderType.FEMALE);
        edit.setDepartment(department);
        edit.setStudentNumber("2026000001");
        edit.setYearOfStudy(2);
        edit.setGpa(new BigDecimal("3.25"));
        edit.setCgpa(new BigDecimal("3.10"));
        edit.setInCourse(true);
        edit.setActive(true);
        edit.setInCampus(false);

        var result = service.updateStudent(id, edit);

        assertEquals("Ada", result.getFirstName());
        assertEquals("2026000001", result.getStudentNumber());
        assertEquals(new BigDecimal("3.25"), result.getGpa());
        assertEquals(department, result.getDepartment());
        assertTrue(result.getInCourse());
        assertFalse(result.getInCampus());
    }

    @Test
    void attendanceRecordEditUsesIntegerDatabaseId() {
        AttendanceRecordServiceImpl service = new AttendanceRecordServiceImpl();
        AttendanceRecordRepository records = mock(AttendanceRecordRepository.class);
        ReflectionTestUtils.setField(service, "attendanceRecordRepository", records);
        AttendanceRecord record = new AttendanceRecord();
        record.setAttendanceRecordId(42);
        record.setLate(false);
        when(records.findById(42)).thenReturn(Optional.of(record));
        when(records.save(record)).thenReturn(record);
        var edit = new com.mrtkyr.classqroom.dto.iu.DtoAttendanceRecordIU();
        edit.setLate(true);

        assertTrue(service.updateAttendanceRecord(42, edit).getLate());
        assertEquals(42, record.getAttendanceRecordId());
    }
}
