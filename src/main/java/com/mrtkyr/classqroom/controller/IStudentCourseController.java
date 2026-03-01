package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoStudentCourse;
import com.mrtkyr.classqroom.dto.iu.DtoStudentCourseIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.entity.StudentCourseId;

import java.util.List;

public interface IStudentCourseController {
    RootEntity<DtoStudentCourse> saveStudentCourse(DtoStudentCourseIU dtoStudentCourseIU);
    List<DtoStudentCourse> getAllStudentCourses();
    RootEntity<DtoStudentCourse> getStudentCourseById(StudentCourseId id);
    void deleteStudentCourse(StudentCourseId id);
    RootEntity<DtoStudentCourse> updateStudentCourse(StudentCourseId id, DtoStudentCourseIU dtoStudentCourseIU);
}
