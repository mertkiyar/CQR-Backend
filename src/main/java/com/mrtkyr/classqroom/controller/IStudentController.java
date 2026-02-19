package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoStudent;
import com.mrtkyr.classqroom.dto.DtoStudentIU;

import java.util.List;
import java.util.UUID;

public interface IStudentController {

    public DtoStudent saveStudent(DtoStudentIU dtoStudentIU);

    public List<DtoStudent> getAllStudents();

    public DtoStudent getStudentById(UUID id);

    public void deleteStudent(UUID id);

    public DtoStudent updateStudent(UUID id, DtoStudentIU dtoStudentIU);
}
