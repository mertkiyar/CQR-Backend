package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Attendance;
import com.mrtkyr.classqroom.entity.AttendanceSession;
import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.enums.AttendanceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAttendanceRecordIU {

    @NotNull(message = "Student cannot be null!")
    private Student student;

    @NotNull(message = "Attendance cannot be null!")
    private Attendance attendance;

    @NotNull(message = "Attendance Session cannot be null!")
    private AttendanceSession attendanceSession;

    @NotNull(message = "Attendance Type cannot be null!")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AttendanceType attendanceType;

    private BigDecimal currentLat;

    private BigDecimal currentLong;

    @NotNull(message = "Device ID cannot be null!")
    private UUID deviceId;

    @NotNull(message = "Client IP cannot be null!")
    @Size(max = 45, message = "Client IP cannot be upper than 45 chars!")
    private String clientIp;

    @NotNull(message = "Attend Time cannot be null!")
    private LocalDateTime attendAt;

    private Boolean late;
}
