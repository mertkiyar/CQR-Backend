package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoLecturerCourse;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerCourseIU;
import com.mrtkyr.classqroom.entity.LecturerCourseId;

import java.util.List;

public interface ILecturerCourseService {
    DtoLecturerCourse saveLecturerCourse(DtoLecturerCourseIU dtoLecturerCourseIU);
    List<DtoLecturerCourse> getAllLecturerCourses();
    DtoLecturerCourse getLecturerCourseById(LecturerCourseId id);
    void deleteLecturerCourse(LecturerCourseId id);
    DtoLecturerCourse updateLecturerCourse(LecturerCourseId id, DtoLecturerCourseIU dtoLecturerCourseIU);
}
