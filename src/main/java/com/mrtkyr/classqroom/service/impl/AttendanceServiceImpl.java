package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoAttendance;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceIU;
import com.mrtkyr.classqroom.entity.Attendance;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.AttendanceRepository;
import com.mrtkyr.classqroom.service.IAttendanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttendanceServiceImpl implements IAttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public DtoAttendance saveAttendance(DtoAttendanceIU dtoAttendanceIU) {
        DtoAttendance dtoAttendance = new DtoAttendance();
        Attendance attendance = new Attendance();
        BeanUtils.copyProperties(dtoAttendanceIU, attendance);
        attendance = attendanceRepository.save(attendance);
        BeanUtils.copyProperties(attendance, dtoAttendance);
        return dtoAttendance;
    }

    @Override
    public List<DtoAttendance> getAllAttendances() {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        List<DtoAttendance> dtoAttendanceList = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            DtoAttendance dtoAttendance = new DtoAttendance();
            BeanUtils.copyProperties(attendance, dtoAttendance);
            dtoAttendanceList.add(dtoAttendance);
        }
        return dtoAttendanceList;
    }

    @Override
    public DtoAttendance getAttendanceById(UUID id) {
        DtoAttendance dtoAttendance = new DtoAttendance();
        Optional<Attendance> optAttendance = attendanceRepository.findById(id);
        if(optAttendance.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Attendance attendance = optAttendance.get();
        BeanUtils.copyProperties(attendance, dtoAttendance);
        return dtoAttendance;
    }

    @Override
    public void deleteAttendance(UUID id) {
        Optional<Attendance> optAttendance = attendanceRepository.findById(id);
        if(optAttendance.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Attendance attendance = optAttendance.get();
        attendanceRepository.delete(attendance);
    }

    @Override
    public DtoAttendance updateAttendance(UUID id, DtoAttendanceIU dtoAttendanceIU) {
        DtoAttendance dtoAttendance = new DtoAttendance();
        Optional<Attendance> optAttendance = attendanceRepository.findById(id);
        if(optAttendance.isPresent()) {
            Attendance attendance = optAttendance.get();
            attendance.setNfcPath(dtoAttendanceIU.getNfcPath());
            attendance.setAllowedRadiusMeters(dtoAttendanceIU.getAllowedRadiusMeters());
            attendance.setAttendanceType(dtoAttendanceIU.getAttendanceType());
            attendance.setSessionHours(dtoAttendanceIU.getSessionHours());
            attendance.setActive(dtoAttendanceIU.isActive());
            Attendance updatedAttendance = attendanceRepository.save(attendance);
            BeanUtils.copyProperties(updatedAttendance, dtoAttendance);
            return dtoAttendance;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
