package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    //EXAMPLES
    @Query(value = "SELECT u.name FROM students s JOIN users u ON u.user_id = s.student_id", nativeQuery = true)
    List<Student> findAllStudentsByName();

    @Query(value = "from Student s where s.studentNumber = :id")
    Student getStudentbyId(UUID id);
}
