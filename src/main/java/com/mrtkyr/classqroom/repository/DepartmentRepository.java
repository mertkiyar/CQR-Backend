package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Short> {
}

