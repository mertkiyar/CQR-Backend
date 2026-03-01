package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoLecturerCourse;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerCourseIU;
import com.mrtkyr.classqroom.entity.LecturerCourse;
import com.mrtkyr.classqroom.entity.LecturerCourseId;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.LecturerCourseRepository;
import com.mrtkyr.classqroom.service.ILecturerCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LecturerCourseServiceImpl implements ILecturerCourseService {

    @Autowired
    private LecturerCourseRepository lecturerCourseRepository;

    @Override
    public DtoLecturerCourse saveLecturerCourse(DtoLecturerCourseIU dtoLecturerCourseIU) {
        LecturerCourse lecturerCourse = new LecturerCourse();
        DtoLecturerCourse dtoLecturerCourse = new DtoLecturerCourse();
        BeanUtils.copyProperties(dtoLecturerCourseIU, lecturerCourse);
        lecturerCourse = lecturerCourseRepository.save(lecturerCourse);
        BeanUtils.copyProperties(lecturerCourse, dtoLecturerCourse);
        return dtoLecturerCourse;
    }

    @Override
    public List<DtoLecturerCourse> getAllLecturerCourses() {
        List<LecturerCourse> lecturerCourseList = lecturerCourseRepository.findAll();
        List<DtoLecturerCourse> dtoLecturerCourseList = new ArrayList<>();
        for (LecturerCourse lecturerCourse : lecturerCourseList) {
            DtoLecturerCourse dtoLecturerCourse = new DtoLecturerCourse();
            BeanUtils.copyProperties(lecturerCourse, dtoLecturerCourse);
            dtoLecturerCourseList.add(dtoLecturerCourse);
        }
        return dtoLecturerCourseList;
    }

    @Override
    public DtoLecturerCourse getLecturerCourseById(LecturerCourseId id) {
        DtoLecturerCourse dtoLecturerCourse = new DtoLecturerCourse();
        Optional<LecturerCourse> optLecturerCourse = lecturerCourseRepository.findById(id);
        if (optLecturerCourse.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        LecturerCourse lecturerCourse = optLecturerCourse.get();
        BeanUtils.copyProperties(lecturerCourse, dtoLecturerCourse);
        return dtoLecturerCourse;
    }

    @Override
    public void deleteLecturerCourse(LecturerCourseId id) {
        Optional<LecturerCourse> optLecturerCourse = lecturerCourseRepository.findById(id);
        if (optLecturerCourse.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        LecturerCourse lecturerCourse = optLecturerCourse.get();
        lecturerCourseRepository.delete(lecturerCourse);
    }

    @Override
    public DtoLecturerCourse updateLecturerCourse(LecturerCourseId id, DtoLecturerCourseIU dtoLecturerCourseIU) {
        DtoLecturerCourse dtoLecturerCourse = new DtoLecturerCourse();
        Optional<LecturerCourse> optLecturerCourse = lecturerCourseRepository.findById(id);
        if (optLecturerCourse.isPresent()) {
            LecturerCourse lecturerCourse = optLecturerCourse.get();
            lecturerCourse.setLecturer(dtoLecturerCourseIU.getLecturer());
            lecturerCourse.setCourse(dtoLecturerCourseIU.getCourse());
            lecturerCourse.setActive(dtoLecturerCourseIU.isActive());
            LecturerCourse updatedLecturerCourse = lecturerCourseRepository.save(lecturerCourse);
            BeanUtils.copyProperties(updatedLecturerCourse, dtoLecturerCourse);
            return dtoLecturerCourse;
        } else {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
    }
}
