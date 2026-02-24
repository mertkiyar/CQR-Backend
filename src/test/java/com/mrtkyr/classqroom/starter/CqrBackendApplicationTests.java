package com.mrtkyr.classqroom.starter;

import com.mrtkyr.classqroom.dto.DtoLecturer;
import com.mrtkyr.classqroom.dto.DtoStudent;
import com.mrtkyr.classqroom.service.ILecturerService;
import com.mrtkyr.classqroom.service.IStudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = {CqrBackendApplication.class})
class CqrBackendApplicationTests {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ILecturerService lecturerService;

    @Test
    public void testGetStudentById() {
        DtoStudent dtoStudent = studentService.getStudentById(UUID.fromString("1e4b8149-047d-40b6-a012-09b2bc049c9e"));
        if (dtoStudent != null) {
            System.out.println("Name : " + dtoStudent.getFirstName());
        }
    }

    @Test
    public void testGetLecturerById() {
        DtoLecturer dtoLecturer = lecturerService.getLecturerById(UUID.fromString("c1bec187-6119-441d-9a6f-ee368144ff05"));
        if (dtoLecturer != null) {
            System.out.println("Name : " + dtoLecturer.getFirstName());
        }
    }
}
