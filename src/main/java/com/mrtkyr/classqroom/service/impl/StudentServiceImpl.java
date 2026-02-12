package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.entity.Student;
import com.mrtkyr.classqroom.repository.StudentRepository;
import com.mrtkyr.classqroom.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(UUID id) {
        return studentRepository.getStudentById(id);
    }

    @Override
    public void deleteStudent(UUID id) {
        boolean isNotDeleted = true;
        while (isNotDeleted) {
            Student deleteStudent = getStudentById(id);
            if (deleteStudent != null) {
                studentRepository.delete(deleteStudent);
                isNotDeleted = false;
            }
        }
    }

    @Override
    public Student updateStudent(UUID id, Student updateStudent) {
        Student currentStudent = getStudentById(id);
        if (currentStudent != null) {
            currentStudent.setStudentNumber(updateStudent.getStudentNumber());
            currentStudent.setYearOfStudy(updateStudent.getYearOfStudy());
            currentStudent.setGpa(updateStudent.getGpa());
            currentStudent.setCgpa(updateStudent.getCgpa());
            currentStudent.setActive(updateStudent.isActive());
            currentStudent.setInCampus(updateStudent.isInCampus());

            return studentRepository.save(currentStudent);
        }
        return null;
    }
}
