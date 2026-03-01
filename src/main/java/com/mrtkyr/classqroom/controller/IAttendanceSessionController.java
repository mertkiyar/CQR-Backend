package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoAttendanceSession;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceSessionIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;
import java.util.UUID;

public interface IAttendanceSessionController {
    RootEntity<DtoAttendanceSession> saveAttendanceSession(DtoAttendanceSessionIU dtoAttendanceSessionIU);
    List<DtoAttendanceSession> getAllAttendanceSessions();
    RootEntity<DtoAttendanceSession> getAttendanceSessionById(UUID id);
    void deleteAttendanceSession(UUID id);
    RootEntity<DtoAttendanceSession> updateAttendanceSession(UUID id, DtoAttendanceSessionIU dtoAttendanceSessionIU);
}
