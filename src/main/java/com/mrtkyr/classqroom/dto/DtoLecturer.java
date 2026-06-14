package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.enums.AcademicRole;
import com.mrtkyr.classqroom.enums.AcademicTitle;
import com.mrtkyr.classqroom.enums.GenderType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoLecturer {
    private String firstName;
    private String lastName;
    private GenderType gender;
    private Department department;
    private AcademicTitle lecturerTitle;
    private AcademicRole lecturerRole;
    private String phone;
    private String extPhone;
    private boolean inCourse;
    private LocalDateTime createdAt;
}
