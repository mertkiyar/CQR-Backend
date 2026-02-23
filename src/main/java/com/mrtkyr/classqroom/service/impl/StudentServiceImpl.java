package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoStudent;
import com.mrtkyr.classqroom.dto.DtoStudentIU;
import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.StudentRepository;
import com.mrtkyr.classqroom.service.IStudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public DtoStudent saveStudent(DtoStudentIU dtoStudentIU) {
        Student student = new Student();
        DtoStudent dtoStudent = new DtoStudent();
        BeanUtils.copyProperties(dtoStudentIU, student);
        student = studentRepository.save(student);
        BeanUtils.copyProperties(student, dtoStudent);
        return dtoStudent;
    }

    @Override
    public List<DtoStudent> getAllStudents() {
        List<DtoStudent> dtoStudentList = new ArrayList<>();
        List<Student> studentList = studentRepository.findAll();
        for (Student student : studentList) {
            DtoStudent dtoStudent = new DtoStudent();
            BeanUtils.copyProperties(student, dtoStudent);
            dtoStudentList.add(dtoStudent);
        }
        return dtoStudentList;
    }

    @Override
    public DtoStudent getStudentById(UUID id) {
        DtoStudent dtoStudent = new DtoStudent();
        Optional<Student> optStudent = studentRepository.findById(id);

        if(optStudent.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Student student = optStudent.get();
        BeanUtils.copyProperties(student, dtoStudent);
        return dtoStudent;
    }

    @Override
    public void deleteStudent(UUID id) {
        Optional<Student> optStudent = studentRepository.findById(id);
        if(optStudent.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Student student = optStudent.get();
        studentRepository.delete(student);
    }

    @Override
    public DtoStudent updateStudent(UUID id, DtoStudentIU dtoStudentIU) {
        DtoStudent dtoStudent = new DtoStudent();
        Optional<Student> optStudent = studentRepository.findById(id);
        if (optStudent.isPresent()) {
            Student student = optStudent.get();
            student.setYearOfStudy(dtoStudentIU.getYearOfStudy());
            student.setGpa(dtoStudentIU.getGpa());
            student.setCgpa(dtoStudentIU.getCgpa());
            student.setActive(dtoStudentIU.isActive());
            student.setInCampus(dtoStudentIU.isInCampus());
            Student updatedStudent = studentRepository.save(student);
            BeanUtils.copyProperties(updatedStudent, dtoStudent);
            return dtoStudent;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
