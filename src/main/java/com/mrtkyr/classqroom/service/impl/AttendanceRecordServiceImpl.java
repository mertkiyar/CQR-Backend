package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoAttendanceRecord;
import com.mrtkyr.classqroom.dto.iu.DtoAttendanceRecordIU;
import com.mrtkyr.classqroom.entity.AttendanceRecord;
import com.mrtkyr.classqroom.entity.AttendanceSession;
import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.AttendanceRecordRepository;
import com.mrtkyr.classqroom.repository.AttendanceSessionRepository;
import com.mrtkyr.classqroom.repository.LecturerCourseRepository;
import com.mrtkyr.classqroom.repository.StudentRepository;
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

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceSessionRepository attendanceSessionRepository;

    @Autowired
    private LecturerCourseRepository lecturerCourseRepository;

    @Override
    public DtoAttendanceRecord saveAttendanceRecord(DtoAttendanceRecordIU dtoAttendanceRecordIU) {
        Student student = studentRepository.findById(dtoAttendanceRecordIU.getStudentId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoAttendanceRecordIU.getStudentId().toString())));

        AttendanceSession attendanceSession = attendanceSessionRepository.findById(dtoAttendanceRecordIU.getAttendanceSessionId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoAttendanceRecordIU.getAttendanceSessionId().toString())));

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setStudent(student);
        attendanceRecord.setAttendanceSession(attendanceSession);
        attendanceRecord.setAttendanceType(dtoAttendanceRecordIU.getAttendanceType());
        attendanceRecord.setCurrentLat(dtoAttendanceRecordIU.getCurrentLat());
        attendanceRecord.setCurrentLong(dtoAttendanceRecordIU.getCurrentLong());
        attendanceRecord.setAttendAt(dtoAttendanceRecordIU.getAttendAt());
        attendanceRecord.setLate(dtoAttendanceRecordIU.getLate());
        attendanceRecord.setDeviceId(dtoAttendanceRecordIU.getDeviceId());
        attendanceRecord.setClientIp(dtoAttendanceRecordIU.getClientIp());

        AttendanceRecord saved = attendanceRecordRepository.save(attendanceRecord);
        DtoAttendanceRecord dtoAttendanceRecord = new DtoAttendanceRecord();
        BeanUtils.copyProperties(saved, dtoAttendanceRecord);
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
        if (optAttendanceRecord.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        BeanUtils.copyProperties(optAttendanceRecord.get(), dtoAttendanceRecord);
        return dtoAttendanceRecord;
    }

    @Override
    public void deleteAttendanceRecord(UUID id) {
        Optional<AttendanceRecord> optAttendanceRecord = attendanceRecordRepository.findById(id);
        if (optAttendanceRecord.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        attendanceRecordRepository.delete(optAttendanceRecord.get());
    }

    @Override
    public DtoAttendanceRecord updateAttendanceRecord(UUID id, DtoAttendanceRecordIU dtoAttendanceRecordIU) {
        DtoAttendanceRecord dtoAttendanceRecord = new DtoAttendanceRecord();
        Optional<AttendanceRecord> optAttendanceRecord = attendanceRecordRepository.findById(id);
        if (optAttendanceRecord.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        AttendanceRecord attendanceRecord = optAttendanceRecord.get();
        attendanceRecord.setLate(dtoAttendanceRecordIU.getLate());
        AttendanceRecord updated = attendanceRecordRepository.save(attendanceRecord);
        BeanUtils.copyProperties(updated, dtoAttendanceRecord);
        return dtoAttendanceRecord;
    }

    @Override
    public List<DtoAttendanceRecord> getAttendanceRecordsByStudent(UUID studentId) {
        List<AttendanceRecord> records = attendanceRecordRepository.findByStudent_UserIdOrderByAttendAtDesc(studentId);
        List<DtoAttendanceRecord> dtoList = new ArrayList<>();
        for (AttendanceRecord record : records) {
            DtoAttendanceRecord dto = new DtoAttendanceRecord();
            BeanUtils.copyProperties(record, dto);
            dto.setStudent(record.getStudent());
            dto.setAttendanceSession(record.getAttendanceSession());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<DtoAttendanceRecord> getAttendanceRecordsByLecturer(UUID lecturerId) {
        List<Course> courses = lecturerCourseRepository.findActiveCoursesByLecturerId(lecturerId);
        List<DtoAttendanceRecord> dtoList = new ArrayList<>();
        if (courses == null || courses.isEmpty()) return dtoList;

        List<AttendanceRecord> records = attendanceRecordRepository.findByAttendanceSession_Attendance_CourseInOrderByAttendAtDesc(courses);
        for (AttendanceRecord record : records) {
            DtoAttendanceRecord dto = new DtoAttendanceRecord();
            BeanUtils.copyProperties(record, dto);
            dto.setStudent(record.getStudent());
            dto.setAttendanceSession(record.getAttendanceSession());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
