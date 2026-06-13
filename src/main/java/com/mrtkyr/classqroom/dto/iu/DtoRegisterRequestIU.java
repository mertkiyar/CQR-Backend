package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.enums.GenderType;
import com.mrtkyr.classqroom.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoRegisterRequestIU {


    @NotNull(message = "First Name cannot be null!")
    private String firstName;

    @NotNull(message = "Last Name cannot be null!")
    private String lastName;

    @NotNull(message = "Email cannot be null!")
    private String email;

    @NotNull(message = "Password cannot be null!")
    private String password;

    @NotNull(message = "Gender cannot be null!")
    private GenderType gender;

    @NotNull(message = "User Type cannot be null!")
    private UserType userType;

    @NotNull(message = "Department cannot be null!")
    private int departmentId;
}
