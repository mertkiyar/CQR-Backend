package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.entity.Attendance;
import com.mrtkyr.classqroom.entity.AttendanceSession;
import com.mrtkyr.classqroom.repository.AttendanceRepository;
import com.mrtkyr.classqroom.repository.AttendanceSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionScheduler {

    private static final Logger log = LoggerFactory.getLogger(SessionScheduler.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceSessionRepository attendanceSessionRepository;

    @Scheduled(fixedDelay = 30000)
    @Transactional
    public void rotateActiveSessions() {
        List<Attendance> activeAttendances = attendanceRepository.findAllByActiveTrue();

        for (Attendance attendance : activeAttendances) {
            List<AttendanceSession> activeSessions =
                    attendanceSessionRepository.findByAttendanceAndActiveTrue(attendance);

            for (AttendanceSession session : activeSessions) {
                session.setActive(false);
                attendanceSessionRepository.save(session);
            }

            AttendanceSession newSession = new AttendanceSession();
            newSession.setAttendance(attendance);
            newSession.setActive(true);
            newSession.setExpiresAt(LocalDateTime.now().plusSeconds(30));
            
            String randomCode = String.format("%06d", new java.util.Random().nextInt(1000000));
            newSession.setSixDigitCode(randomCode);
            
            attendanceSessionRepository.save(newSession);

            log.info("New QR/6-Digit session created for attendanceId={}", attendance.getAttendanceId());
        }
    }
}
