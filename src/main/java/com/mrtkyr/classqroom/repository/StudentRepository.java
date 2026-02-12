package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student getStudentById(UUID id);

    boolean deleteStudentById(UUID id);
}
