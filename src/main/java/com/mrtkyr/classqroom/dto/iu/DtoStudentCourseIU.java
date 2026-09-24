package com.mrtkyr.classqroom.dto.iu;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudentCourseIU {

    @NotNull(message = "Student cannot be null!")
    @Valid
    private DtoUserIdReference student;

    @NotNull(message = "Course cannot be null!")
    @Valid
    private DtoCourseIdReference course;

    @NotNull(message = "Active variable cannot be null!")
    private Boolean active;
}
