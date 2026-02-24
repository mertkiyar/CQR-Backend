package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoLecturer;
import com.mrtkyr.classqroom.dto.DtoLecturerIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;
import java.util.UUID;

public interface ILecturerController {

    RootEntity<DtoLecturer> saveLecturer(DtoLecturerIU dtoLecturerIU);

    List<DtoLecturer> getAllLecturers();

    RootEntity<DtoLecturer> getLecturerById(UUID id);

    void deleteLecturer(UUID id);

    RootEntity<DtoLecturer> updateLecturer(UUID id, DtoLecturerIU dtoLecturerIU);
}
