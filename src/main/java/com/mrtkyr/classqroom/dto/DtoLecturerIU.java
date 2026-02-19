package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.enums.AcademicRole;
import com.mrtkyr.classqroom.enums.AcademicTitle;
import com.mrtkyr.classqroom.enums.GenderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLecturerIU {

    @NotNull(message = "First Name cannot be null!")
    @Size(min = 2, max = 32, message = "First Name must be between 2 and 32 characters!")
    private String firstName;

    @NotNull(message = "Last Name cannot be null!")
    @Size(min = 2, max = 32, message = "Last Name must be between 2 and 32 characters!")
    private String lastName;

    @NotNull(message = "Gender cannot be null!")
    private GenderType gender;

    @NotNull(message = "Department Id cannot be null!")
    private int departmentId;

    @NotNull(message = "Lecturer Title cannot be null!")
    private AcademicTitle lecturerTitle;

    @NotNull(message = "Lecturer Role cannot be null!")
    private AcademicRole lecturerRole;

    @Size(min = 12, max = 15, message = "Phone Number must be between 12 and 15 characters!")
    private String phone;

    @Size(min = 4, max = 4, message = "Phone Number must be 4 characters!")
    private String extPhone;

    @NotNull(message = "In Course cannot be null!")
    private boolean inCourse;
}
