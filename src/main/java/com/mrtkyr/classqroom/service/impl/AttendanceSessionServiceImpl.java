package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoAttendanceSession;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceSessionIU;
import com.mrtkyr.classqroom.entity.AttendanceSession;
import com.mrtkyr.classqroom.entity.Attendance;
import com.mrtkyr.classqroom.repository.AttendanceRepository;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.AttendanceSessionRepository;
import com.mrtkyr.classqroom.service.IAttendanceSessionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttendanceSessionServiceImpl implements IAttendanceSessionService {

    @Autowired
    private AttendanceSessionRepository attendanceSessionRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public DtoAttendanceSession saveAttendanceSession(DtoAttendanceSessionIU dtoAttendanceSessionIU) {
        DtoAttendanceSession dtoAttendanceSession = new DtoAttendanceSession();
        AttendanceSession attendanceSession = new AttendanceSession();
        BeanUtils.copyProperties(dtoAttendanceSessionIU, attendanceSession);
        attendanceSession = attendanceSessionRepository.save(attendanceSession);
        BeanUtils.copyProperties(attendanceSession, dtoAttendanceSession);
        return dtoAttendanceSession;
    }

    @Override
    public List<DtoAttendanceSession> getAllAttendanceSessions() {
        List<AttendanceSession> attendanceSessionList = attendanceSessionRepository.findAll();
        List<DtoAttendanceSession> dtoAttendanceSessionList = new ArrayList<>();
        for (AttendanceSession attendanceSession : attendanceSessionList) {
            DtoAttendanceSession dtoAttendanceSession = new DtoAttendanceSession();
            BeanUtils.copyProperties(attendanceSession, dtoAttendanceSession);
            dtoAttendanceSessionList.add(dtoAttendanceSession);
        }
        return dtoAttendanceSessionList;
    }

    @Override
    public DtoAttendanceSession getAttendanceSessionById(UUID id) {
        DtoAttendanceSession dtoAttendanceSession = new DtoAttendanceSession();
        Optional<AttendanceSession> optAttendanceSession = attendanceSessionRepository.findById(id);
        if (optAttendanceSession.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        AttendanceSession attendanceSession = optAttendanceSession.get();
        BeanUtils.copyProperties(attendanceSession, dtoAttendanceSession);
        return dtoAttendanceSession;
    }

    @Override
    public void deleteAttendanceSession(UUID id) {
        Optional<AttendanceSession> optAttendanceSession = attendanceSessionRepository.findById(id);
        if (optAttendanceSession.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        attendanceSessionRepository.delete(optAttendanceSession.get());
    }

    @Override
    public DtoAttendanceSession updateAttendanceSession(UUID id, DtoAttendanceSessionIU dtoAttendanceSessionIU) {
        DtoAttendanceSession dtoAttendanceSession = new DtoAttendanceSession();
        Optional<AttendanceSession> optAttendanceSession = attendanceSessionRepository.findById(id);
        if (optAttendanceSession.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        AttendanceSession attendanceSession = optAttendanceSession.get();
        attendanceSession.setActive(dtoAttendanceSessionIU.isActive());
        AttendanceSession updated = attendanceSessionRepository.save(attendanceSession);
        BeanUtils.copyProperties(updated, dtoAttendanceSession);
        return dtoAttendanceSession;
    }

    @Override
    public DtoAttendanceSession getCurrentSessionByAttendanceId(UUID attendanceId) {
        DtoAttendanceSession dtoAttendanceSession = new DtoAttendanceSession();
        Optional<AttendanceSession> optSession = attendanceSessionRepository
                .findFirstByAttendance_AttendanceIdAndActiveTrueOrderByCreatedAtDesc(attendanceId);
        if (optSession.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_ACTIVE_SESSION, attendanceId.toString()));
        }
        BeanUtils.copyProperties(optSession.get(), dtoAttendanceSession);
        return dtoAttendanceSession;
    }

    @Override
    public DtoAttendanceSession getCurrentSessionByNfcPath(UUID nfcPath) {
        Optional<Attendance> optAttendance = attendanceRepository.findByNfcPath(nfcPath);
        if (optAttendance.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, nfcPath.toString()));
        }
        return getCurrentSessionByAttendanceId(optAttendance.get().getAttendanceId());
    }
}
