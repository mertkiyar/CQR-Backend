package com.mrtkyr.classqroom.starter;

import com.mrtkyr.classqroom.dto.DtoUser;
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
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class RegistrationAndCourseTests {
    @Test
    void publicRegistrationCannotCreateAdmin() {
        AuthServiceImpl service = new AuthServiceImpl();
        UserRepository users = mock(UserRepository.class);
        ReflectionTestUtils.setField(service, "userRepository", users);

        assertThrows(BaseException.class, () -> service.register(new DtoRegisterRequestIU(
                "Admin", "User", "admin@example.com", "password", GenderType.OTHER, UserType.ADMIN, 1)));
        verifyNoInteractions(users);
    }

    @Test
    void authorityComesFromPersistedUserType() {
        User user = new User();
        user.setUserType(UserType.ADMIN);
        assertEquals("ROLE_ADMIN", user.getAuthorities().iterator().next().getAuthority());
    }

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
        Department department = new Department();
        department.setId((short) 1);
        when(departments.findById((short) 1)).thenReturn(Optional.of(department));
        when(users.saveAndFlush(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setCreatedAt(LocalDateTime.now());
            return saved;
        });

        DtoUser response = service.register(new DtoRegisterRequestIU("Ece", "Demir", "ece@example.com", "test-password",
                GenderType.FEMALE, UserType.LECTURER, 1));

        assertEquals(1, response.getDepartmentId());
        assertNotNull(response.getCreatedAt());
        assertFalse(JsonMapper.builder().build().writeValueAsString(response).contains("password"));
        verify(users).saveAndFlush(org.mockito.ArgumentMatchers.argThat(user ->
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
