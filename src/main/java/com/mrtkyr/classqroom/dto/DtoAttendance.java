package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.enums.AttendanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAttendance {
    private UUID attendanceId;
    private Course course;
    private UUID nfcPath;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer allowedRadiusMeters;
    private AttendanceType attendanceType;
    private Short sessionHours;
    private LocalDateTime startedAt;
    private LocalDateTime expiresAt;
    private boolean active;
}
