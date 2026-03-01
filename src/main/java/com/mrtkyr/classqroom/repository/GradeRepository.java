package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Grade;
import com.mrtkyr.classqroom.entity.GradeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, GradeId> {
}
