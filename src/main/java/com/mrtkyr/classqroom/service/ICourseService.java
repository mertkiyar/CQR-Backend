package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoCourse;
import com.mrtkyr.classqroom.dto.iu.DtoCourseIU;

import java.util.List;
import java.util.UUID;

public interface ICourseService {
    DtoCourse saveCourse(DtoCourseIU dtoCourseIU);
    List<DtoCourse> getAllCourses();
    DtoCourse getCourseById(UUID id);
    void deleteCourse(UUID id);
    DtoCourse updateCourse(UUID id, DtoCourseIU dtoCourseIU);
}
