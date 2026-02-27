package com.mrtkyr.classqroom.dto.iu;

import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.enums.AttendanceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
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
public class DtoAttendanceIU {

    @NotNull(message = "Course cannot be null!")
    private Course course;

    @NotNull(message = "Nfc Path cannot be null!")
    private UUID nfcPath;

    @NotNull(message = "Latitude cannot be null!")
    private BigDecimal latitude;

    @NotNull(message = "Longitude cannot be null!")
    private BigDecimal longitude;

    @NotNull(message = "Allowed Radius cannot be null!")
    private int allowedRadiusMeters;

    @NotNull(message = "Attendance Type cannot be null!")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AttendanceType attendanceType;

    @NotNull(message = "Session Hours cannot be null!")
    @Min(value = 0, message = "Session Hours cannot be negative!")
    @Max(value = 10, message = "Session Hours cannot be upper than 10!")
    private Short sessionHours;

    @NotNull(message = "Start Time cannot be null!")
    private LocalDateTime startedAt;

    @NotNull(message = "Expire Time cannot be null!")
    private LocalDateTime expiresAt;

    @NotNull(message = "Active variable cannot be null!")
    private boolean active;
}
