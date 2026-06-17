package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoCourse;
import com.mrtkyr.classqroom.dto.DtoLecturerCourse;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerCourseIU;
import com.mrtkyr.classqroom.entity.LecturerCourseId;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;
import java.util.UUID;

public interface ILecturerCourseController {
    RootEntity<DtoLecturerCourse> saveLecturerCourse(DtoLecturerCourseIU dtoLecturerCourseIU);
    List<DtoLecturerCourse> getAllLecturerCourses();
    RootEntity<DtoLecturerCourse> getLecturerCourseById(LecturerCourseId id);
    void deleteLecturerCourse(LecturerCourseId id);
    RootEntity<DtoLecturerCourse> updateLecturerCourse(LecturerCourseId id, DtoLecturerCourseIU dtoLecturerCourseIU);
    RootEntity<List<DtoCourse>> getActiveCoursesByLecturer(UUID lecturerId);
}
