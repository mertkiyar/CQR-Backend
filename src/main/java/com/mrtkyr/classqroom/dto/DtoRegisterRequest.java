package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.enums.GenderType;
import com.mrtkyr.classqroom.enums.UserType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private GenderType gender;
    private UserType userType;
    private int departmentId;
}
