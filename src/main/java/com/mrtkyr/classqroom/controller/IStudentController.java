package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoStudent;
import com.mrtkyr.classqroom.dto.DtoStudentIU;

import java.util.List;
import java.util.UUID;

public interface IStudentController {

    DtoStudent saveStudent(DtoStudentIU dtoStudentIU);

    List<DtoStudent> getAllStudents();

    DtoStudent getStudentById(UUID id);

    void deleteStudent(UUID id);

    DtoStudent updateStudent(UUID id, DtoStudentIU dtoStudentIU);
}
