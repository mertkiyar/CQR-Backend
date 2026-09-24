package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoCourse;
import com.mrtkyr.classqroom.dto.iu.DtoCourseIU;
import com.mrtkyr.classqroom.entity.Course;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.CourseRepository;
import com.mrtkyr.classqroom.repository.DepartmentRepository;
import com.mrtkyr.classqroom.repository.LanguageRepository;
import com.mrtkyr.classqroom.service.ICourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public DtoCourse saveCourse(DtoCourseIU dtoCourseIU) {
        if (courseRepository.existsByCourseCode(dtoCourseIU.getCourseCode())) {
            throw new BaseException(new ErrorMessage(MessageType.RECORD_ALREADY_EXIST,
                    "Course code " + dtoCourseIU.getCourseCode()));
        }
        DtoCourse dtoCourse = new DtoCourse();
        Course course = new Course();
        BeanUtils.copyProperties(dtoCourseIU, course);
        course.setLanguage(languageRepository.findById(dtoCourseIU.getLanguage().getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                        "Language " + dtoCourseIU.getLanguage().getId()))));
        course.setDepartment(departmentRepository.findById(dtoCourseIU.getDepartment().getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                        "Department " + dtoCourseIU.getDepartment().getId()))));
        course = courseRepository.save(course);
        BeanUtils.copyProperties(course, dtoCourse);
        return dtoCourse;
    }

    @Override
    public List<DtoCourse> getAllCourses() {
        List<DtoCourse> dtoCourseList = new ArrayList<>();
        List<Course> courseList = courseRepository.findAll();
        for (Course course : courseList) {
            DtoCourse dtoCourse = new DtoCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            dtoCourseList.add(dtoCourse);
        }
        return dtoCourseList;
    }

    @Override
    public DtoCourse getCourseById(UUID id) {
        DtoCourse dtoCourse = new DtoCourse();
        Optional<Course> optCourse = courseRepository.findById(id);
        if(optCourse.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Course course = optCourse.get();
        BeanUtils.copyProperties(course, dtoCourse);
        return dtoCourse;
    }

    @Override
    public void deleteCourse(UUID id) {
        Optional<Course> optCourse = courseRepository.findById(id);
        if(optCourse.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Course course = optCourse.get();
        courseRepository.delete(course);
    }

    @Override
    public DtoCourse updateCourse(UUID id, DtoCourseIU dtoCourseIU) {
        DtoCourse dtoCourse = new DtoCourse();
        Optional<Course> optCourse = courseRepository.findById(id);
        if(optCourse.isPresent()){
            Course course = optCourse.get();
            if (!course.getCourseCode().trim().equals(dtoCourseIU.getCourseCode().trim())
                    && courseRepository.existsByCourseCode(dtoCourseIU.getCourseCode())) {
                throw new BaseException(new ErrorMessage(MessageType.RECORD_ALREADY_EXIST,
                        "Course code " + dtoCourseIU.getCourseCode()));
            }
            course.setCourseName(dtoCourseIU.getCourseName());
            course.setCourseCode(dtoCourseIU.getCourseCode());
            course.setCourseEcts(dtoCourseIU.getCourseEcts());
            course.setCourseCredit(dtoCourseIU.getCourseCredit());
            course.setHoursTheoretical(dtoCourseIU.getHoursTheoretical());
            course.setHoursPractical(dtoCourseIU.getHoursPractical());
            course.setMinAttendancePercent(dtoCourseIU.getMinAttendancePercent());
            course.setLanguage(languageRepository.findById(dtoCourseIU.getLanguage().getId())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                            "Language " + dtoCourseIU.getLanguage().getId()))));
            course.setOnline(dtoCourseIU.getOnline());
            course.setElective(dtoCourseIU.getElective());
            course.setDepartment(departmentRepository.findById(dtoCourseIU.getDepartment().getId())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                            "Department " + dtoCourseIU.getDepartment().getId()))));
            course = courseRepository.save(course);
            BeanUtils.copyProperties(course, dtoCourse);
            return dtoCourse;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
