package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoAttendanceRecord;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceRecordIU;

import java.util.List;
import java.util.UUID;

public interface IAttendanceRecordService {
    DtoAttendanceRecord saveAttendanceRecord(DtoAttendanceRecordIU dtoAttendanceRecordIU);
    List<DtoAttendanceRecord> getAllAttendanceRecords();
    DtoAttendanceRecord getAttendanceRecordById(Integer id);
    void deleteAttendanceRecord(Integer id);
    DtoAttendanceRecord updateAttendanceRecord(Integer id, DtoAttendanceRecordIU dtoAttendanceRecordIU);
    List<DtoAttendanceRecord> getAttendanceRecordsByStudent(UUID studentId);
    List<DtoAttendanceRecord> getAttendanceRecordsByLecturer(UUID lecturerId);
}
