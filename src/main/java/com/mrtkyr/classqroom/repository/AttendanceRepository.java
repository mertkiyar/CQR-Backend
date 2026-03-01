package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
}
