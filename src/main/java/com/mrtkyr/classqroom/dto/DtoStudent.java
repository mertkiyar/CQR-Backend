package com.mrtkyr.classqroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudent {
    private String studentNumber;
    private int yearOfStudy;
}
