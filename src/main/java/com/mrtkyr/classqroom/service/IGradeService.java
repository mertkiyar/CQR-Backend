package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoGrade;
import com.mrtkyr.classqroom.dto.iu.DtoGradeIU;
import com.mrtkyr.classqroom.entity.GradeId;

import java.util.List;

public interface IGradeService {
    DtoGrade saveGrade(DtoGradeIU dtoGradeIU);
    List<DtoGrade> getAllGrades();
    DtoGrade getGradeById(GradeId id);
    void deleteGrade(GradeId id);
    DtoGrade updateGrade(GradeId id, DtoGradeIU dtoGradeIU);
}
