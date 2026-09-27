package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoAttendanceRecord;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceRecordIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;
import java.util.UUID;

public interface IAttendanceRecordController {
    RootEntity<DtoAttendanceRecord> saveAttendanceRecord(DtoAttendanceRecordIU dtoAttendanceRecordIU);
    List<DtoAttendanceRecord> getAllAttendanceRecords();
    RootEntity<DtoAttendanceRecord> getAttendanceRecordById(Integer id);
    void deleteAttendanceRecord(Integer id);
    RootEntity<DtoAttendanceRecord> updateAttendanceRecord(Integer id, DtoAttendanceRecordIU dtoAttendanceRecordIU);
}
