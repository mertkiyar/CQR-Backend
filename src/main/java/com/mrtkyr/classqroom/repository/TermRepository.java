package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Term;
import com.mrtkyr.classqroom.enums.TermType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Short> {
    boolean existsByTermName(String termName);
    boolean existsByAcademicYearStartAndTermType(Short academicYearStart, TermType termType);
}
