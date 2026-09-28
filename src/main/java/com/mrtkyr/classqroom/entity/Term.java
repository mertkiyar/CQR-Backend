package com.mrtkyr.classqroom.entity;

import com.mrtkyr.classqroom.enums.TermType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Table(name = "terms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Term {

    @Id
    @Column(name = "term_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "term_name", length = 50, nullable = false, unique = true)
    private String termName;

    @Column(name = "academic_year_start", nullable = false)
    private Short academicYearStart;

    @Column(name = "term_start_date", nullable = false)
    private LocalDate termStartDate;

    @Column(name = "term_end_date", nullable = false)
    private LocalDate termEndDate;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "term_type", nullable = false)
    private TermType termType;
}
