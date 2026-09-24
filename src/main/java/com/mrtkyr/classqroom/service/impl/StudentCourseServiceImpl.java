package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoStudentCourse;
import com.mrtkyr.classqroom.dto.iu.DtoStudentCourseIU;
import com.mrtkyr.classqroom.entity.StudentCourse;
import com.mrtkyr.classqroom.entity.StudentCourseId;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.StudentCourseRepository;
import com.mrtkyr.classqroom.repository.StudentRepository;
import com.mrtkyr.classqroom.repository.CourseRepository;
import com.mrtkyr.classqroom.service.IStudentCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentCourseServiceImpl implements IStudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional
    public DtoStudentCourse saveStudentCourse(DtoStudentCourseIU dtoStudentCourseIU) {
        if (dtoStudentCourseIU.getStudent() == null || dtoStudentCourseIU.getCourse() == null) {
            throw new BaseException(new ErrorMessage(MessageType.MISSING_REQUIRED_FIELD, "Student and course are required"));
        }
        UUID studentId = dtoStudentCourseIU.getStudent().getUserId();
        UUID courseId = dtoStudentCourseIU.getCourse().getCourseId();
        if (studentId == null || courseId == null) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_ID, "Student and course IDs are required"));
        }
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                        "Student profile " + studentId)));
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                        "Course " + courseId)));

        StudentCourseId id = new StudentCourseId(studentId, courseId);
        StudentCourse studentCourse = studentCourseRepository.findById(id).orElseGet(StudentCourse::new);
        studentCourse.setId(id);
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setActive(dtoStudentCourseIU.getActive());
        DtoStudentCourse dtoStudentCourse = new DtoStudentCourse();
        studentCourse = studentCourseRepository.save(studentCourse);
        BeanUtils.copyProperties(studentCourse, dtoStudentCourse);
        return dtoStudentCourse;
    }

    @Override
    public List<DtoStudentCourse> getAllStudentCourses() {
        List<StudentCourse> studentCourseList = studentCourseRepository.findAll();
        List<DtoStudentCourse> dtoStudentCourseList = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourseList) {
            DtoStudentCourse dtoStudentCourse = new DtoStudentCourse();
            BeanUtils.copyProperties(studentCourse, dtoStudentCourse);
            dtoStudentCourseList.add(dtoStudentCourse);
        }
        return dtoStudentCourseList;
    }

    @Override
    public DtoStudentCourse getStudentCourseById(StudentCourseId id) {
        DtoStudentCourse dtoStudentCourse = new DtoStudentCourse();
        Optional<StudentCourse> optStudentCourse = studentCourseRepository.findById(id);
        if(optStudentCourse.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        StudentCourse studentCourse = optStudentCourse.get();
        BeanUtils.copyProperties(studentCourse, dtoStudentCourse);
        return dtoStudentCourse;
    }

    @Override
    public void deleteStudentCourse(StudentCourseId id) {
        Optional<StudentCourse> optStudentCourse = studentCourseRepository.findById(id);
        if(optStudentCourse.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        StudentCourse studentCourse = optStudentCourse.get();
        studentCourseRepository.delete(studentCourse);
    }

    @Override
    @Transactional
    public DtoStudentCourse updateStudentCourse(StudentCourseId id, DtoStudentCourseIU dtoStudentCourseIU) {
        if (dtoStudentCourseIU.getStudent() == null || dtoStudentCourseIU.getCourse() == null
                || !id.getStudentId().equals(dtoStudentCourseIU.getStudent().getUserId())
                || !id.getCourseId().equals(dtoStudentCourseIU.getCourse().getCourseId())) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_ID,
                    "Association IDs must match the path"));
        }
        DtoStudentCourse dtoStudentCourse = new DtoStudentCourse();
        Optional<StudentCourse> optStudentCourse = studentCourseRepository.findById(id);
        if(optStudentCourse.isPresent()) {
            StudentCourse studentCourse = optStudentCourse.get();
            studentCourse.setActive(dtoStudentCourseIU.getActive());
            StudentCourse updatedStudentCourse = studentCourseRepository.save(studentCourse);
            BeanUtils.copyProperties(updatedStudentCourse, dtoStudentCourse);
            return dtoStudentCourse;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
