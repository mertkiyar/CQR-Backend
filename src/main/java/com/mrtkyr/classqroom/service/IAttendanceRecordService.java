package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoAttendanceRecord;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceRecordIU;

import java.util.List;
import java.util.UUID;

public interface IAttendanceRecordService {
    DtoAttendanceRecord saveAttendanceRecord(DtoAttendanceRecordIU dtoAttendanceRecordIU);
    List<DtoAttendanceRecord> getAllAttendanceRecords();
    DtoAttendanceRecord getAttendanceRecordById(UUID id);
    void deleteAttendanceRecord(UUID id);
    DtoAttendanceRecord updateAttendanceRecord(UUID id, DtoAttendanceRecordIU dtoAttendanceRecordIU);
}
