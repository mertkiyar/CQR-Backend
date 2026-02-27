package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoCourse {
    private UUID courseId;
    private String courseName;
    private String courseCode;
    private BigDecimal courseEcts;
    private BigDecimal courseCredit;
    private Short hoursTheoretical;
    private Short hoursPractical;
    private Integer minAttendancePercent;
    private Language language;
    private Boolean online;
    private Boolean elective;
    private Department department;
    private LocalDateTime createdAt;
}
