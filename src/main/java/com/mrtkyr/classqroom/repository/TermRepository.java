package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Short> {
}
