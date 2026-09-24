package com.mrtkyr.classqroom.starter;

import com.mrtkyr.classqroom.controller.impl.RestAuthControllerImpl;
import com.mrtkyr.classqroom.controller.impl.LecturerCourseControllerImpl;
import com.mrtkyr.classqroom.controller.impl.StudentCourseControllerImpl;
import com.mrtkyr.classqroom.dto.DtoLecturerCourse;
import com.mrtkyr.classqroom.dto.DtoStudentCourse;
import com.mrtkyr.classqroom.exception.GlobalExceptionHandler;
import com.mrtkyr.classqroom.jwt.AuthRequest;
import com.mrtkyr.classqroom.service.IAuthService;
import com.mrtkyr.classqroom.service.ILecturerCourseService;
import com.mrtkyr.classqroom.service.IStudentCourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiErrorResponseTests {
    private MockMvc mockMvc;
    private IAuthService authService;

    @BeforeEach
    void setUp() {
        authService = mock(IAuthService.class);
        RestAuthControllerImpl controller = new RestAuthControllerImpl();
        ReflectionTestUtils.setField(controller, "authService", authService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void wrongPasswordReturnsReadableUnauthorizedResponse() throws java.lang.Exception {
        when(authService.authenticate(any(AuthRequest.class)))
                .thenThrow(new BadCredentialsException("Sensitive internal authentication detail"));

        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"ece@example.com\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.errorCode").value("3004"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid e-mail or password"));
    }

    @Test
    void invalidRegisterBodyReturnsValidationDetails() throws java.lang.Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.errorCode").value("2001"))
                .andExpect(jsonPath("$.errorMessage").isNotEmpty());
    }

    @Test
    void lecturerCourseAcceptsIdOnlyReferences() throws java.lang.Exception {
        ILecturerCourseService service = mock(ILecturerCourseService.class);
        when(service.saveLecturerCourse(any())).thenReturn(new DtoLecturerCourse());
        LecturerCourseControllerImpl controller = new LecturerCourseControllerImpl();
        ReflectionTestUtils.setField(controller, "lecturerCourseService", service);
        MockMvc courseMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        courseMvc.perform(post("/lecturer-courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lecturer\":{\"userId\":\"b68f0ddd-1172-42df-8b6a-917cfe6dce94\"},"
                                + "\"course\":{\"courseId\":\"5345444c-8ef2-4e2d-96c3-24a797972eda\"},"
                                + "\"active\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void studentCourseAcceptsIdOnlyReferences() throws java.lang.Exception {
        IStudentCourseService service = mock(IStudentCourseService.class);
        when(service.saveStudentCourse(any())).thenReturn(new DtoStudentCourse());
        StudentCourseControllerImpl controller = new StudentCourseControllerImpl();
        ReflectionTestUtils.setField(controller, "studentCourseService", service);
        MockMvc courseMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        courseMvc.perform(post("/student-courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"student\":{\"userId\":\"b68f0ddd-1172-42df-8b6a-917cfe6dce94\"},"
                                + "\"course\":{\"courseId\":\"5345444c-8ef2-4e2d-96c3-24a797972eda\"},"
                                + "\"active\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void lecturerCourseMissingIdReturnsValidationError() throws java.lang.Exception {
        LecturerCourseControllerImpl controller = new LecturerCourseControllerImpl();
        ReflectionTestUtils.setField(controller, "lecturerCourseService", mock(ILecturerCourseService.class));
        MockMvc courseMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        courseMvc.perform(post("/lecturer-courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lecturer\":{},\"course\":{\"courseId\":\"5345444c-8ef2-4e2d-96c3-24a797972eda\"},\"active\":true}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("2001"));
    }
}
