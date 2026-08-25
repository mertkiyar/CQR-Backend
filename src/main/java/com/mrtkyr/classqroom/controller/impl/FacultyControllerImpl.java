package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IFacultyController;
import com.mrtkyr.classqroom.dto.DtoFaculty;
import com.mrtkyr.classqroom.dto.iu.DtoFacultyIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IFacultyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyControllerImpl extends RestBaseController implements IFacultyController {

    @Autowired
    private IFacultyService facultyService;

    @PostMapping
    @Override
    public RootEntity<DtoFaculty> saveFaculty(@RequestBody @Valid DtoFacultyIU dtoFacultyIU) {
        return ok(facultyService.saveFaculty(dtoFacultyIU));
    }

    @GetMapping
    @Override
    public List<DtoFaculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoFaculty> getFacultyById(@PathVariable(name = "id") Short id) {
        return ok(facultyService.getFacultyById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteFaculty(@PathVariable(name = "id") Short id) {
        facultyService.deleteFaculty(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoFaculty> updateFaculty(@PathVariable(name = "id") Short id, @RequestBody @Valid DtoFacultyIU dtoFacultyIU) {
        return ok(facultyService.updateFaculty(id, dtoFacultyIU));
    }
}
