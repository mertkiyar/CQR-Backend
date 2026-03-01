package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoFaculty;
import com.mrtkyr.classqroom.dto.iu.DtoFacultyIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;

public interface IFacultyController {
    RootEntity<DtoFaculty> saveFaculty(DtoFacultyIU dtoFacultyIU);
    List<DtoFaculty> getAllFaculties();
    RootEntity<DtoFaculty> getFacultyById(Short id);
    void deleteFaculty(Short id);
    RootEntity<DtoFaculty> updateFaculty(Short id, DtoFacultyIU dtoFacultyIU);
}
