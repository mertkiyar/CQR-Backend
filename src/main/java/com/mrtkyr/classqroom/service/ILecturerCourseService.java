package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoCourse;
import com.mrtkyr.classqroom.dto.DtoLecturerCourse;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerCourseIU;
import com.mrtkyr.classqroom.entity.LecturerCourseId;

import java.util.List;
import java.util.UUID;

public interface ILecturerCourseService {
    DtoLecturerCourse saveLecturerCourse(DtoLecturerCourseIU dtoLecturerCourseIU);
    List<DtoLecturerCourse> getAllLecturerCourses();
    DtoLecturerCourse getLecturerCourseById(LecturerCourseId id);
    void deleteLecturerCourse(LecturerCourseId id);
    DtoLecturerCourse updateLecturerCourse(LecturerCourseId id, DtoLecturerCourseIU dtoLecturerCourseIU);
    List<DtoCourse> getActiveCoursesByLecturer(UUID lecturerId);
}
