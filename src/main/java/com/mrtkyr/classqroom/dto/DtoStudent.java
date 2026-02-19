package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudent {
    private String firstName;
    private String lastName;
    private GenderType gender;
    private int departmentId;
    private String studentNumber;
    private int yearOfStudy;
    private boolean inCourse;
    private boolean active;
    private boolean inCampus;
    private LocalDateTime createdAt;
}
