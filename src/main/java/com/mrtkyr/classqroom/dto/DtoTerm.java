package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.enums.TermType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoTerm {
    private Short id;
    private String termName;
    private Short academicYearStart;
    private LocalDate termStartDate;
    private LocalDate termEndDate;
    private TermType termType;
}
