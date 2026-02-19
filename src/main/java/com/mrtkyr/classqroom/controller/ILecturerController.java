package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoLecturer;
import com.mrtkyr.classqroom.dto.DtoLecturerIU;

import java.util.List;
import java.util.UUID;

public interface ILecturerController {

    DtoLecturer saveLecturer(DtoLecturerIU dtoLecturerIU);

    List<DtoLecturer> getAllLecturers();

    DtoLecturer getLecturerById(UUID id);

    void deleteLecturer(UUID id);

    DtoLecturer updateLecturer(UUID id, DtoLecturerIU dtoLecturerIU);
}
