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
@RequestMapping("/grades")
public class GradeControllerImpl extends RestBaseController implements IGradeController {

    @Autowired
    private IGradeService gradeService;

    @PostMapping
    @Override
    public RootEntity<DtoGrade> saveGrade(@RequestBody @Valid DtoGradeIU dtoGradeIU) {
        return ok(gradeService.saveGrade(dtoGradeIU));
    }

    @GetMapping
    @Override
    public List<DtoGrade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoGrade> getGradeById(@PathVariable(name = "id") GradeId id) {
        return ok(gradeService.getGradeById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteGrade(@PathVariable(name = "id") GradeId id) {
        gradeService.deleteGrade(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoGrade> updateGrade(@PathVariable(name = "id") GradeId id, @RequestBody @Valid DtoGradeIU dtoGradeIU) {
        return ok(gradeService.updateGrade(id, dtoGradeIU));
    }
}
