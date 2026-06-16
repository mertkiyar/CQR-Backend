package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.AttendanceSession;
import com.mrtkyr.classqroom.enums.AttendanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAttendanceRecord {
    private int attendanceRecordId;
    private UUID studentId;
    private AttendanceSession attendanceSession;
    private AttendanceType attendanceType;
    private BigDecimal currentLat;
    private BigDecimal currentLong;
    private UUID deviceId;
    private String clientIp;
    private LocalDateTime attendAt;
    private Boolean late;
}
