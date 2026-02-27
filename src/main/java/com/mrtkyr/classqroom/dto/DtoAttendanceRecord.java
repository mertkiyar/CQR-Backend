package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Attendance;
import com.mrtkyr.classqroom.entity.AttendanceSession;
import com.mrtkyr.classqroom.entity.Student;
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
public class DtoAttendanceRecord {
    private int attendanceRecordId;
    private Student student;
    private Attendance attendance;
    private AttendanceSession attendanceSession;
    private AttendanceType attendanceType;
    private BigDecimal currentLat;
    private BigDecimal currentLong;
    private UUID deviceId;
    private String clientIp;
    private LocalDateTime attendAt;
    private Boolean late;
}
