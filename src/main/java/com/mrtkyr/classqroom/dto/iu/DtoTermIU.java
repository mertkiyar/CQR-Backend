package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.enums.TermType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoTermIU {

    @Size(max = 50)
    @NotBlank(message = "Term Name cannot be empty or null!")
    private String termName;

    @NotNull(message = "Academic Year Start cannot be null!")
    private Short academicYearStart;

    @NotNull(message = "Term Start Date cannot be null!")
    private LocalDate termStartDate;

    @NotNull(message = "Term End Date cannot be null!")
    private LocalDate termEndDate;

    @NotNull(message = "Term Type cannot be null!")
    private TermType termType;
}
