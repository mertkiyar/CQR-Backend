package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.entity.Student;

import java.util.List;
import java.util.UUID;

public interface IStudentService {

    public Student saveStudent(Student student);

    public List<Student> getAllStudents();

    public Student getStudentById(UUID id);

    public void deleteStudent(UUID id);

    public Student updateStudent(UUID id, Student updateStudent);
}
