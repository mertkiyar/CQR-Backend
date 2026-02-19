package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.enums.AcademicRole;
import com.mrtkyr.classqroom.enums.AcademicTitle;
import com.mrtkyr.classqroom.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLecturer {
    private String firstName;
    private String lastName;
    private GenderType gender;
    private int departmentId;
    private AcademicTitle lecturerTitle;
    private AcademicRole lecturerRole;
    private String phone;
    private String extPhone;
    private boolean inCourse;
    private LocalDateTime createdAt;
}
