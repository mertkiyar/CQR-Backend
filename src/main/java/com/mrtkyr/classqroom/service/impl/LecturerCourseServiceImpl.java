package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoCourse;
import com.mrtkyr.classqroom.dto.DtoLecturerCourse;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerCourseIU;
import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.entity.LecturerCourse;
import com.mrtkyr.classqroom.entity.LecturerCourseId;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.LecturerCourseRepository;
import com.mrtkyr.classqroom.repository.LecturerRepository;
import com.mrtkyr.classqroom.repository.CourseRepository;
import com.mrtkyr.classqroom.service.ILecturerCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LecturerCourseServiceImpl implements ILecturerCourseService {

    @Autowired
    private LecturerCourseRepository lecturerCourseRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional
    public DtoLecturerCourse saveLecturerCourse(DtoLecturerCourseIU dtoLecturerCourseIU) {
        if (dtoLecturerCourseIU.getLecturer() == null || dtoLecturerCourseIU.getCourse() == null) {
            throw new BaseException(new ErrorMessage(MessageType.MISSING_REQUIRED_FIELD, "Lecturer and course are required"));
        }
        UUID lecturerId = dtoLecturerCourseIU.getLecturer().getUserId();
        UUID courseId = dtoLecturerCourseIU.getCourse().getCourseId();
        if (lecturerId == null || courseId == null) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_ID, "Lecturer and course IDs are required"));
        }

        var lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                        "Lecturer profile " + lecturerId)));
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                        "Course " + courseId)));

        LecturerCourseId id = new LecturerCourseId(lecturerId, courseId);
        LecturerCourse lecturerCourse = lecturerCourseRepository.findById(id).orElseGet(LecturerCourse::new);
        lecturerCourse.setId(id);
        lecturerCourse.setLecturer(lecturer);
        lecturerCourse.setCourse(course);
        lecturerCourse.setActive(dtoLecturerCourseIU.getActive());
        DtoLecturerCourse dtoLecturerCourse = new DtoLecturerCourse();
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
    @Transactional
    public DtoLecturerCourse updateLecturerCourse(LecturerCourseId id, DtoLecturerCourseIU dtoLecturerCourseIU) {
        if (dtoLecturerCourseIU.getLecturer() == null || dtoLecturerCourseIU.getCourse() == null
                || !id.getLecturerId().equals(dtoLecturerCourseIU.getLecturer().getUserId())
                || !id.getCourseId().equals(dtoLecturerCourseIU.getCourse().getCourseId())) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_ID,
                    "Association IDs must match the path"));
        }
        DtoLecturerCourse dtoLecturerCourse = new DtoLecturerCourse();
        Optional<LecturerCourse> optLecturerCourse = lecturerCourseRepository.findById(id);
        if (optLecturerCourse.isPresent()) {
            LecturerCourse lecturerCourse = optLecturerCourse.get();
            lecturerCourse.setActive(dtoLecturerCourseIU.getActive());
            LecturerCourse updatedLecturerCourse = lecturerCourseRepository.save(lecturerCourse);
            BeanUtils.copyProperties(updatedLecturerCourse, dtoLecturerCourse);
            return dtoLecturerCourse;
        } else {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
    }

    @Override
    public List<DtoCourse> getActiveCoursesByLecturer(UUID lecturerId) {
        List<Course> courses = lecturerCourseRepository.findActiveCoursesByLecturerId(lecturerId);
        List<DtoCourse> dtoCourses = new ArrayList<>();
        for (Course course : courses) {
            DtoCourse dto = new DtoCourse();
            BeanUtils.copyProperties(course, dto);
            dtoCourses.add(dto);
        }
        return dtoCourses;
    }
}
