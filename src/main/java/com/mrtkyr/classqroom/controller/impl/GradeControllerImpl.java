package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IGradeController;
import com.mrtkyr.classqroom.dto.DtoGrade;
import com.mrtkyr.classqroom.dto.iu.DtoGradeIU;
import com.mrtkyr.classqroom.entity.GradeId;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IGradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/student/grade")
public class GradeControllerImpl extends RestBaseController implements IGradeController {

    @Autowired
    private IGradeService gradeService;

    @PostMapping("/save")
    @Override
    public RootEntity<DtoGrade> saveGrade(@RequestBody @Valid DtoGradeIU dtoGradeIU) {
        return ok(gradeService.saveGrade(dtoGradeIU));
    }

    @GetMapping("/list")
    @Override
    public List<DtoGrade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @GetMapping("/get/{id}")
    @Override
    public RootEntity<DtoGrade> getGradeById(@PathVariable(name = "id") GradeId id) {
        return ok(gradeService.getGradeById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteGrade(@PathVariable(name = "id") GradeId id) {
        gradeService.deleteGrade(id);
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoGrade> updateGrade(@PathVariable(name = "id") GradeId id, @RequestBody @Valid DtoGradeIU dtoGradeIU) {
        return ok(gradeService.updateGrade(id, dtoGradeIU));
    }
}
