package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoStudentCourse;
import com.mrtkyr.classqroom.dto.iu.DtoStudentCourseIU;
import com.mrtkyr.classqroom.entity.StudentCourseId;

import java.util.List;

public interface IStudentCourseService {
    DtoStudentCourse saveStudentCourse(DtoStudentCourseIU dtoStudentCourseIU);
    List<DtoStudentCourse> getAllStudentCourses();
    DtoStudentCourse getStudentCourseById(StudentCourseId id);
    void deleteStudentCourse(StudentCourseId id);
    DtoStudentCourse updateStudentCourse(StudentCourseId id, DtoStudentCourseIU dtoStudentCourseIU);
}
