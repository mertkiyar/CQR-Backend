package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoStudent;
import com.mrtkyr.classqroom.dto.iu.DtoStudentIU;

import java.util.List;
import java.util.UUID;

public interface IStudentService {
    DtoStudent saveStudent(DtoStudentIU dtoStudentIU);
    List<DtoStudent> getAllStudents();
    DtoStudent getStudentById(UUID id);
    void deleteStudent(UUID id);
    DtoStudent updateStudent(UUID id, DtoStudentIU dtoStudentIU);
}
