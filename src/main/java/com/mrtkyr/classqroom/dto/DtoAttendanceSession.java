package com.mrtkyr.classqroom.dto;

import com.mrtkyr.classqroom.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAttendanceSession {
    private UUID attendanceSessionId;
    private Attendance attendance;
    private String sixDigitCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active;
}
