package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.enums.GenderType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudent {
    private String firstName;
    private String lastName;
    private GenderType gender;
    private Department department;
    private String studentNumber;
    private Integer yearOfStudy;
    private Boolean inCourse;
    private Boolean active;
    private Boolean inCampus;
    private LocalDateTime createdAt;
}
