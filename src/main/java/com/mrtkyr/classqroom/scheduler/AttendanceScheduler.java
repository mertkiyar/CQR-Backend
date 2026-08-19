package com.mrtkyr.classqroom.scheduler;

import com.mrtkyr.classqroom.entity.Attendance;
import com.mrtkyr.classqroom.repository.AttendanceRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceScheduler {

    private static final Logger log = LoggerFactory.getLogger(AttendanceScheduler.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void deactivateExpiredAttendances() {
        LocalDateTime now = LocalDateTime.now();
        List<Attendance> expiredAttendances = attendanceRepository.findByActiveTrueAndExpiresAtBefore(now);

        if (!expiredAttendances.isEmpty()) {
            for (Attendance attendance : expiredAttendances) {
                attendance.setActive(false);
            }
            attendanceRepository.saveAll(expiredAttendances);
        }
    }
}
