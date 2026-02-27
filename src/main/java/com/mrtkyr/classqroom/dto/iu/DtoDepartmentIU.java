package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Faculty;
import com.mrtkyr.classqroom.entity.Language;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoDepartmentIU {

    @NotNull(message = "Department Name cannot be null!")
    private String departmentName;

    @NotNull(message = "Department Code cannot be null!")
    private String departmentCode;

    @NotNull(message = "Language cannot be null!")
    private Language language;

    @NotNull(message = "Faculty cannot be null!")
    private Faculty faculty;
}
