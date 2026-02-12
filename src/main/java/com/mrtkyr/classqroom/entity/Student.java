package com.mrtkyr.classqroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import java.util.UUID;


@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private UUID id;

    @Column(name = "student_number", nullable = false, unique = true, length = 10)
    private String studentNumber;

    @Column(name = "year_of_study", nullable = false)
    private int yearOfStudy;

    @Column(name = "gpa", precision = 3, scale = 2, nullable = false)
    private BigDecimal gpa;

    @Column(name = "cgpa", precision = 3, scale = 2, nullable = false)
    private BigDecimal cgpa;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "is_in_campus", nullable = false)
    private boolean inCampus;
}
