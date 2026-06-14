package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Faculty;
import com.mrtkyr.classqroom.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoDepartment {
    private Short id;
    private String departmentName;
    private String departmentCode;
    private Language language;
    private Faculty faculty;
    private LocalDateTime createdAt;
}
