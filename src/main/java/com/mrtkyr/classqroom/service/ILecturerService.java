package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoLecturer;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerIU;

import java.util.List;
import java.util.UUID;

public interface ILecturerService {
    DtoLecturer saveLecturer(DtoLecturerIU dtoLecturerIU);
    List<DtoLecturer> getAllLecturers();
    DtoLecturer getLecturerById(UUID id);
    void deleteLecturer(UUID id);
    DtoLecturer updateLecturer(UUID id, DtoLecturerIU dtoLecturerIU);
}
