package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoAttendance;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;
import java.util.UUID;

public interface IAttendanceController {
    RootEntity<DtoAttendance> saveAttendance(DtoAttendanceIU dtoAttendanceIU);
    List<DtoAttendance> getAllAttendances();
    RootEntity<DtoAttendance> getAttendanceById(UUID id);
    void deleteAttendance(UUID id);
    RootEntity<DtoAttendance> updateAttendance(UUID id, DtoAttendanceIU dtoAttendanceIU);
}
