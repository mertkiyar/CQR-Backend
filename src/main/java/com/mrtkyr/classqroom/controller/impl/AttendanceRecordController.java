package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IAttendanceRecordController;
import com.mrtkyr.classqroom.dto.DtoAttendanceRecord;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceRecordIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IAttendanceRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance-records")
public class AttendanceRecordController extends RestBaseController implements IAttendanceRecordController {

    @Autowired
    private IAttendanceRecordService attendanceRecordService;

    @PostMapping
    @Override
    public RootEntity<DtoAttendanceRecord> saveAttendanceRecord(@RequestBody @Valid DtoAttendanceRecordIU dtoAttendanceRecordIU) {
        return ok(attendanceRecordService.saveAttendanceRecord(dtoAttendanceRecordIU));
    }

    @GetMapping
    @Override
    public List<DtoAttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordService.getAllAttendanceRecords();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoAttendanceRecord> getAttendanceRecordById(@PathVariable(name = "id") UUID id) {
        return ok(attendanceRecordService.getAttendanceRecordById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteAttendanceRecord(@PathVariable(name = "id") UUID id) {
        attendanceRecordService.deleteAttendanceRecord(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoAttendanceRecord> updateAttendanceRecord(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoAttendanceRecordIU dtoAttendanceRecordIU) {
        return ok(attendanceRecordService.updateAttendanceRecord(id, dtoAttendanceRecordIU));
    }

    @GetMapping("/students/{studentId}")
    public RootEntity<List<DtoAttendanceRecord>> getAttendanceRecordsByStudent(@PathVariable(name = "studentId") UUID studentId) {
        return ok(attendanceRecordService.getAttendanceRecordsByStudent(studentId));
    }

    @GetMapping("/lecturers/{lecturerId}")
    public RootEntity<List<DtoAttendanceRecord>> getAttendanceRecordsByLecturer(@PathVariable(name = "lecturerId") UUID lecturerId) {
        return ok(attendanceRecordService.getAttendanceRecordsByLecturer(lecturerId));
    }
}
