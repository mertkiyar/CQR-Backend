package com.mrtkyr.classqroom.starter;

import com.mrtkyr.classqroom.dto.iu.DtoLecturerCourseIU;
import com.mrtkyr.classqroom.dto.iu.DtoCourseIdReference;
import com.mrtkyr.classqroom.dto.iu.DtoUserIdReference;
import com.mrtkyr.classqroom.dto.iu.DtoRegisterRequestIU;
import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.entity.Lecturer;
import com.mrtkyr.classqroom.entity.User;
import com.mrtkyr.classqroom.enums.GenderType;
import com.mrtkyr.classqroom.enums.UserType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.repository.CourseRepository;
import com.mrtkyr.classqroom.repository.DepartmentRepository;
import com.mrtkyr.classqroom.repository.LecturerRepository;
import com.mrtkyr.classqroom.repository.UserRepository;
import com.mrtkyr.classqroom.service.impl.AuthServiceImpl;
import com.mrtkyr.classqroom.service.impl.LecturerCourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegistrationAndCourseTests {
    @Test
    void lecturerRegistrationPersistsLecturerProfile() {
        AuthServiceImpl service = new AuthServiceImpl();
        UserRepository users = mock(UserRepository.class);
        DepartmentRepository departments = mock(DepartmentRepository.class);
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
        ReflectionTestUtils.setField(service, "userRepository", users);
        ReflectionTestUtils.setField(service, "departmentRepository", departments);
        ReflectionTestUtils.setField(service, "passwordEncoder", encoder);
        when(users.findUserByEmail("ece@example.com")).thenReturn(Optional.empty());
        when(departments.findById((short) 1)).thenReturn(Optional.of(new Department()));
        when(users.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.register(new DtoRegisterRequestIU("Ece", "Demir", "ece@example.com", "test-password",
                GenderType.FEMALE, UserType.LECTURER, 1));

        verify(users).save(org.mockito.ArgumentMatchers.argThat(user ->
                user instanceof Lecturer lecturer && lecturer.getLecturerTitle() != null
                        && lecturer.getLecturerRole() != null));
    }

    @Test
    void missingLecturerProfileHasAUsefulError() {
        LecturerCourseServiceImpl service = new LecturerCourseServiceImpl();
        LecturerRepository lecturers = mock(LecturerRepository.class);
        CourseRepository courses = mock(CourseRepository.class);
        ReflectionTestUtils.setField(service, "lecturerRepository", lecturers);
        ReflectionTestUtils.setField(service, "courseRepository", courses);
        UUID lecturerId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(lecturers.findById(lecturerId)).thenReturn(Optional.empty());

        BaseException error = assertThrows(BaseException.class,
                () -> service.saveLecturerCourse(new DtoLecturerCourseIU(
                        new DtoUserIdReference(lecturerId), new DtoCourseIdReference(courseId), true)));

        assertEquals("1001", error.getMessageType().getCode());
    }
}
