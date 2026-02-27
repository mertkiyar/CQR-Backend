package com.mrtkyr.classqroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @Column(name = "course_id")
    @UuidGenerator
    private UUID courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "course_code",length = 7, nullable = false)
    private String courseCode;

    @Column(name = "course_ects", precision = 3, scale = 1)
    private BigDecimal courseEcts;

    @Column(name = "course_credit", precision = 3, scale = 1)
    private BigDecimal courseCredit;

    @Column(name = "hours_theoretical", nullable = false)
    private Short hoursTheoretical;

    @Column(name = "hours_practical", nullable = false)
    private Short hoursPractical;

    @Column(name = "min_attendance_percent")
    private Integer minAttendancePercent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "is_online", nullable = false)
    private boolean online;

    @Column(name = "is_elective", nullable = false)
    private boolean elective;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
