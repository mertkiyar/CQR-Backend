package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoAttendanceSession;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceSessionIU;

import java.util.List;
import java.util.UUID;

public interface IAttendanceSessionService {
    DtoAttendanceSession saveAttendanceSession(DtoAttendanceSessionIU dtoAttendanceSessionIU);
    List<DtoAttendanceSession> getAllAttendanceSessions();
    DtoAttendanceSession getAttendanceSessionById(UUID id);
    void deleteAttendanceSession(UUID id);
    DtoAttendanceSession updateAttendanceSession(UUID id, DtoAttendanceSessionIU dtoAttendanceSessionIU);
}
