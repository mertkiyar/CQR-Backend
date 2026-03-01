package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoAttendanceRecord;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceRecordIU;
import com.mrtkyr.classqroom.entity.AttendanceRecord;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.AttendanceRecordRepository;
import com.mrtkyr.classqroom.service.IAttendanceRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttendanceRecordServiceImpl implements IAttendanceRecordService {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Override
    public DtoAttendanceRecord saveAttendanceRecord(DtoAttendanceRecordIU dtoAttendanceRecordIU) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        DtoAttendanceRecord dtoAttendanceRecord = new DtoAttendanceRecord();
        BeanUtils.copyProperties(dtoAttendanceRecordIU, attendanceRecord);
        attendanceRecord = attendanceRecordRepository.save(attendanceRecord);
        BeanUtils.copyProperties(attendanceRecord, dtoAttendanceRecord);
        return dtoAttendanceRecord;
    }

    @Override
    public List<DtoAttendanceRecord> getAllAttendanceRecords() {
        List<DtoAttendanceRecord> dtoAttendanceRecordList = new ArrayList<>();
        List<AttendanceRecord> attendanceRecordList = attendanceRecordRepository.findAll();
        for (AttendanceRecord attendanceRecord : attendanceRecordList) {
            DtoAttendanceRecord dtoAttendanceRecord = new DtoAttendanceRecord();
            BeanUtils.copyProperties(attendanceRecord, dtoAttendanceRecord);
            dtoAttendanceRecordList.add(dtoAttendanceRecord);
        }
        return dtoAttendanceRecordList;
    }

    @Override
    public DtoAttendanceRecord getAttendanceRecordById(UUID id) {
        DtoAttendanceRecord dtoAttendanceRecord = new DtoAttendanceRecord();
        Optional<AttendanceRecord> optAttendanceRecord = attendanceRecordRepository.findById(id);
        if(optAttendanceRecord.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        AttendanceRecord attendanceRecord = optAttendanceRecord.get();
        BeanUtils.copyProperties(attendanceRecord, dtoAttendanceRecord);
        return dtoAttendanceRecord;
    }

    @Override
    public void deleteAttendanceRecord(UUID id) {
        Optional<AttendanceRecord> optAttendanceRecord = attendanceRecordRepository.findById(id);
        if(optAttendanceRecord.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        AttendanceRecord attendanceRecord = optAttendanceRecord.get();
        attendanceRecordRepository.delete(attendanceRecord);
    }

    @Override
    public DtoAttendanceRecord updateAttendanceRecord(UUID id, DtoAttendanceRecordIU dtoAttendanceRecordIU) {
        DtoAttendanceRecord dtoAttendanceRecord = new DtoAttendanceRecord();
        Optional<AttendanceRecord> optAttendanceRecord = attendanceRecordRepository.findById(id);
        if(optAttendanceRecord.isPresent()) {
            AttendanceRecord attendanceRecord = optAttendanceRecord.get();
            attendanceRecord.setLate(dtoAttendanceRecordIU.getLate());
            return dtoAttendanceRecord;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
