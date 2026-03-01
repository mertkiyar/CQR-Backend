package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IAttendanceSessionController;
import com.mrtkyr.classqroom.dto.DtoAttendanceSession;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceSessionIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IAttendanceSessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/attendance-session") //TODO student/attendance/session
public class AttendanceSessionControllerImpl extends RestBaseController implements IAttendanceSessionController {

    @Autowired
    private IAttendanceSessionService attendanceSessionService;

    @PostMapping("/save")
    @Override
    public RootEntity<DtoAttendanceSession> saveAttendanceSession(@RequestBody @Valid DtoAttendanceSessionIU dtoAttendanceSessionIU) {
        return ok(attendanceSessionService.saveAttendanceSession(dtoAttendanceSessionIU));
    }

    @GetMapping("/list")
    @Override
    public List<DtoAttendanceSession> getAllAttendanceSessions() {
        return attendanceSessionService.getAllAttendanceSessions();
    }

    @GetMapping("/get/{id}")
    @Override
    public RootEntity<DtoAttendanceSession> getAttendanceSessionById(@PathVariable(name = "id") UUID id) {
        return ok(attendanceSessionService.getAttendanceSessionById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteAttendanceSession(@PathVariable(name = "id") UUID id) {
        attendanceSessionService.deleteAttendanceSession(id);
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoAttendanceSession> updateAttendanceSession(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoAttendanceSessionIU dtoAttendanceSessionIU) {
        return ok(attendanceSessionService.updateAttendanceSession(id, dtoAttendanceSessionIU));
    }
}
