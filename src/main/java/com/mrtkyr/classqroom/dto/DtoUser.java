package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.enums.GenderType;
import com.mrtkyr.classqroom.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {
    private String firstName;
    private String lastName;
    private GenderType gender;
    private UserType userType;
    private int departmentId;
    private LocalDateTime createdAt;
}
