package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoStudent;
import com.mrtkyr.classqroom.dto.iu.DtoStudentIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;
import java.util.UUID;

public interface IStudentController {

    RootEntity<DtoStudent> saveStudent(DtoStudentIU dtoStudentIU);

    List<DtoStudent> getAllStudents();

    RootEntity<DtoStudent> getStudentById(UUID id);

    void deleteStudent(UUID id);

    RootEntity<DtoStudent> updateStudent(UUID id, DtoStudentIU dtoStudentIU);
}
