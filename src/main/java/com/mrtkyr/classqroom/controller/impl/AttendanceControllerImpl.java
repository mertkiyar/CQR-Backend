package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IAttendanceController;
import com.mrtkyr.classqroom.dto.DtoAttendance;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IAttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/attendance")
public class AttendanceControllerImpl extends RestBaseController implements IAttendanceController {

    @Autowired
    private IAttendanceService attendanceService;

    @PostMapping("/save")
    @Override
    public RootEntity<DtoAttendance> saveAttendance(@RequestBody @Valid DtoAttendanceIU dtoAttendanceIU) {
        return ok(attendanceService.saveAttendance(dtoAttendanceIU));
    }

    @GetMapping("/list")
    @Override
    public List<DtoAttendance> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @GetMapping("/get/{id}")
    @Override
    public RootEntity<DtoAttendance> getAttendanceById(@PathVariable(name = "id") UUID id) {
        return ok(attendanceService.getAttendanceById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteAttendance(@PathVariable(name = "id") UUID id) {
        attendanceService.deleteAttendance(id);
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoAttendance> updateAttendance(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoAttendanceIU dtoAttendanceIU) {
        return ok(attendanceService.updateAttendance(id, dtoAttendanceIU));
    }
}
