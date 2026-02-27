package com.mrtkyr.classqroom.entity;

import com.mrtkyr.classqroom.enums.AttendanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attendance_records", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "attendance_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecord {

    @Id
    @Column(name = "attendance_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendanceRecordId;


    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attendance_session_id", nullable = false)
    private AttendanceSession attendanceSession;

    @Column(name = "attendance_type")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AttendanceType attendanceType;

    @Column(name = "current_lat", precision = 9, scale = 6)
    private BigDecimal currentLat;

    @Column(name = "current_long", precision = 9, scale = 6)
    private BigDecimal currentLong;

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "client_ip", nullable = false)
    private InetAddress clientIp;

    @Column(name = "attend_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime attendAt;

    @Column(name = "is_late")
    private Boolean late;
}
