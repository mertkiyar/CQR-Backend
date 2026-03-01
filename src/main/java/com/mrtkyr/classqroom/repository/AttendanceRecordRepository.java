package com.mrtkyr.classqroom.repository;

import com.mrtkyr.classqroom.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, UUID> {
}
