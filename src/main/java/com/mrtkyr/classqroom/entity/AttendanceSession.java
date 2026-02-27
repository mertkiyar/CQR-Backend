package com.mrtkyr.classqroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attendance_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSession {

    @Id
    @Column(name = "attendance_session_id")
    @UuidGenerator
    private UUID attendanceSessionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

    @Column(name = "six_digit_code", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String sixDigitCode;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_active", nullable = false)
    private boolean active;
}
