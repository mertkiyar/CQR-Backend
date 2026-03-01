package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoCourse;
import com.mrtkyr.classqroom.dto.iu.DtoCourseIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;
import java.util.UUID;

public interface ICourseController {
    RootEntity<DtoCourse> saveCourse(DtoCourseIU dtoCourseIU);
    List<DtoCourse> getAllCourses();
    RootEntity<DtoCourse> getCourseById(UUID id);
    void deleteCourse(UUID id);
    RootEntity<DtoCourse> updateCouse(UUID id, DtoCourseIU dtoCourseIU);
}
