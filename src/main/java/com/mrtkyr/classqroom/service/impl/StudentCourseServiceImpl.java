package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoStudentCourse;
import com.mrtkyr.classqroom.dto.iu.DtoStudentCourseIU;
import com.mrtkyr.classqroom.entity.StudentCourse;
import com.mrtkyr.classqroom.entity.StudentCourseId;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.StudentCourseRepository;
import com.mrtkyr.classqroom.service.IStudentCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseServiceImpl implements IStudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public DtoStudentCourse saveStudentCourse(DtoStudentCourseIU dtoStudentCourseIU) {
        StudentCourse studentCourse = new StudentCourse();
        DtoStudentCourse dtoStudentCourse = new DtoStudentCourse();
        BeanUtils.copyProperties(dtoStudentCourseIU, studentCourse);
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
    public DtoStudentCourse updateStudentCourse(StudentCourseId id, DtoStudentCourseIU dtoStudentCourseIU) {
        DtoStudentCourse dtoStudentCourse = new DtoStudentCourse();
        Optional<StudentCourse> optStudentCourse = studentCourseRepository.findById(id);
        if(optStudentCourse.isPresent()) {
            StudentCourse studentCourse = optStudentCourse.get();
            studentCourse.setStudent(dtoStudentCourseIU.getStudent());
            studentCourse.setCourse(dtoStudentCourseIU.getCourse());
            studentCourse.setActive(dtoStudentCourse.isActive());
            StudentCourse updatedStudentCourse = studentCourseRepository.save(studentCourse);
            BeanUtils.copyProperties(updatedStudentCourse, dtoStudentCourse);
            return dtoStudentCourse;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
