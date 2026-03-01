package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoAttendanceSession;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceSessionIU;
import com.mrtkyr.classqroom.entity.AttendanceSession;
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
        if(optAttendanceSession.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        AttendanceSession attendanceSession = optAttendanceSession.get();
        BeanUtils.copyProperties(attendanceSession, dtoAttendanceSession);
        return dtoAttendanceSession;
    }

    @Override
    public void deleteAttendanceSession(UUID id) {
        Optional<AttendanceSession> optAttendanceSession = attendanceSessionRepository.findById(id);
        if(optAttendanceSession.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        AttendanceSession attendanceSession = optAttendanceSession.get();
        attendanceSessionRepository.delete(attendanceSession);
    }

    @Override
    public DtoAttendanceSession updateAttendanceSession(UUID id, DtoAttendanceSessionIU dtoAttendanceSessionIU) {
        DtoAttendanceSession dtoAttendanceSession = new DtoAttendanceSession();
        Optional<AttendanceSession> optAttendanceSession = attendanceSessionRepository.findById(id);
        if(optAttendanceSession.isPresent()) {
            AttendanceSession attendanceSession = optAttendanceSession.get();
            attendanceSession.setActive(dtoAttendanceSessionIU.isActive());
            return dtoAttendanceSession;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
