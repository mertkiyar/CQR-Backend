package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoFaculty;
import com.mrtkyr.classqroom.dto.iu.DtoFacultyIU;

import java.util.List;

public interface IFacultyService {
    DtoFaculty saveFaculty(DtoFacultyIU dtoFacultyIU);
    List<DtoFaculty> getAllFaculties();
    DtoFaculty getFacultyById(Short id);
    void deleteFaculty(Short id);
    DtoFaculty updateFaculty(Short id, DtoFacultyIU dtoFacultyIU);
}
