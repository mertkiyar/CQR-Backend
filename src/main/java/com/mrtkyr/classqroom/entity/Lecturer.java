package com.mrtkyr.classqroom.entity;

import com.mrtkyr.classqroom.enums.AcademicRole;
import com.mrtkyr.classqroom.enums.AcademicTitle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lecturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "lecturer_id")
public class Lecturer extends User {

    @Column(name = "lecturer_title", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AcademicTitle lecturerTitle;

    @Column(name = "lecturer_role", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AcademicRole lecturerRole;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "ext_phone", length = 4)
    private String extPhone;
}
