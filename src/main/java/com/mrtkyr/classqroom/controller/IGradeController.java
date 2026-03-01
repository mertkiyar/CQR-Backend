package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoGrade;
import com.mrtkyr.classqroom.dto.iu.DtoGradeIU;
import com.mrtkyr.classqroom.entity.GradeId;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;

public interface IGradeController {
    RootEntity<DtoGrade> saveGrade(DtoGradeIU dtoGradeIU);
    List<DtoGrade> getAllGrades();
    RootEntity<DtoGrade> getGradeById(GradeId id);
    void deleteGrade(GradeId id);
    RootEntity<DtoGrade> updateGrade(GradeId id, DtoGradeIU dtoGradeIU);
}
