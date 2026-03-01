package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, UUID> {
}
