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
@RequestMapping("/attendance-sessions")
public class AttendanceSessionControllerImpl extends RestBaseController implements IAttendanceSessionController {

    @Autowired
    private IAttendanceSessionService attendanceSessionService;

    @PostMapping
    @Override
    public RootEntity<DtoAttendanceSession> saveAttendanceSession(@RequestBody @Valid DtoAttendanceSessionIU dtoAttendanceSessionIU) {
        return ok(attendanceSessionService.saveAttendanceSession(dtoAttendanceSessionIU));
    }

    @GetMapping
    @Override
    public List<DtoAttendanceSession> getAllAttendanceSessions() {
        return attendanceSessionService.getAllAttendanceSessions();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoAttendanceSession> getAttendanceSessionById(@PathVariable(name = "id") UUID id) {
        return ok(attendanceSessionService.getAttendanceSessionById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteAttendanceSession(@PathVariable(name = "id") UUID id) {
        attendanceSessionService.deleteAttendanceSession(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoAttendanceSession> updateAttendanceSession(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoAttendanceSessionIU dtoAttendanceSessionIU) {
        return ok(attendanceSessionService.updateAttendanceSession(id, dtoAttendanceSessionIU));
    }

    @GetMapping("/current/{attendanceId}")
    @Override
    public RootEntity<DtoAttendanceSession> getCurrentSessionByAttendanceId(@PathVariable(name = "attendanceId") UUID attendanceId) {
        return ok(attendanceSessionService.getCurrentSessionByAttendanceId(attendanceId));
    }

    @GetMapping("/nfc/{nfcPath}")
    @Override
    public RootEntity<DtoAttendanceSession> getCurrentSessionByNfcPath(@PathVariable(name = "nfcPath") UUID nfcPath) {
        return ok(attendanceSessionService.getCurrentSessionByNfcPath(nfcPath));
    }

    @GetMapping("/code/{sixDigitCode}")
    @Override
    public RootEntity<DtoAttendanceSession> getCurrentSessionBySixDigitCode(@PathVariable(name = "sixDigitCode") String sixDigitCode) {
        return ok(attendanceSessionService.getCurrentSessionBySixDigitCode(sixDigitCode));
    }
}
