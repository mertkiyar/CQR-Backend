package com.mrtkyr.classqroom.entity;

import com.mrtkyr.classqroom.enums.AcademicRole;
import com.mrtkyr.classqroom.enums.AcademicTitle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lecturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "lecturer_id")
public class Lecturer extends User {

    @Column(name = "lecturer_title")
    private AcademicTitle lecturerTitle;

    @Column(name = "lecturer_role", nullable = false)
    private AcademicRole lecturerRole;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "ext_phone", unique = true)
    private String extPhone;
}
