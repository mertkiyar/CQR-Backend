package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoAttendance;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceIU;

import java.util.List;
import java.util.UUID;

public interface IAttendanceService {
    DtoAttendance saveAttendance(DtoAttendanceIU dtoAttendanceIU);
    List<DtoAttendance> getAllAttendances();
    DtoAttendance getAttendanceById(UUID id);
    void deleteAttendance(UUID id);
    DtoAttendance updateAttendance(UUID id, DtoAttendanceIU dtoAttendanceIU);

}
