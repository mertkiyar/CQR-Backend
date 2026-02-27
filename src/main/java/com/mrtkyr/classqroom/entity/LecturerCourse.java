package com.mrtkyr.classqroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lecturer_course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerCourse {

    @EmbeddedId
    private LecturerCourseId id;

    @MapsId("lecturerId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "lecturer_id", nullable = false)
    private Lecturer lecturer;

    @MapsId("courseId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrolled_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime enrolledAt;

    @Column(name = "is_active", nullable = false)
    private boolean active;
}
