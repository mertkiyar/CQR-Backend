package com.mrtkyr.classqroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoFaculty {
    private Short facultyId;
    private String facultyName;
    private LocalDateTime createdAt;
}
