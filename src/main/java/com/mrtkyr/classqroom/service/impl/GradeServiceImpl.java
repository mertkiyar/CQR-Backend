package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoGrade;
import com.mrtkyr.classqroom.dto.iu.DtoGradeIU;
import com.mrtkyr.classqroom.entity.Grade;
import com.mrtkyr.classqroom.entity.GradeId;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.GradeRepository;
import com.mrtkyr.classqroom.service.IGradeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements IGradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public DtoGrade saveGrade(DtoGradeIU dtoGradeIU) {
        DtoGrade dtoGrade = new DtoGrade();
        Grade grade = new Grade();
        BeanUtils.copyProperties(dtoGradeIU, grade);
        grade = gradeRepository.save(grade);
        BeanUtils.copyProperties(grade, dtoGrade);
        return dtoGrade;
    }

    @Override
    public List<DtoGrade> getAllGrades() {
        List<Grade> gradeList = gradeRepository.findAll();
        List<DtoGrade> dtoGradeList = new ArrayList<>();
        for (Grade grade : gradeList) {
            DtoGrade dtoGrade = new DtoGrade();
            BeanUtils.copyProperties(grade, dtoGrade);
            dtoGradeList.add(dtoGrade);
        }
        return dtoGradeList;
    }

    @Override
    public DtoGrade getGradeById(GradeId id) {
        DtoGrade dtoGrade = new DtoGrade();
        Optional<Grade> optGrade = gradeRepository.findById(id);
        if (optGrade.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Grade grade = optGrade.get();
        BeanUtils.copyProperties(grade, dtoGrade);
        return dtoGrade;
    }

    @Override
    public void deleteGrade(GradeId id) {
        Optional<Grade> optGrade = gradeRepository.findById(id);
        if (optGrade.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Grade grade = optGrade.get();
        gradeRepository.delete(grade);
    }

    @Override
    public DtoGrade updateGrade(GradeId id, DtoGradeIU dtoGradeIU) {
        DtoGrade dtoGrade = new DtoGrade();
        Optional<Grade> optGrade = gradeRepository.findById(id);
        if (optGrade.isPresent()) {
            Grade grade = optGrade.get();
            grade.setAssessmentType(dtoGradeIU.getAssessmentType());
            grade.setGrade(dtoGradeIU.getGrade());
            Grade updatedGrade = gradeRepository.save(grade);
            BeanUtils.copyProperties(updatedGrade, dtoGrade);
            return dtoGrade;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
