package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.ILecturerController;
import com.mrtkyr.classqroom.dto.DtoLecturer;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.ILecturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lecturers")
public class LecturerControllerImpl extends RestBaseController implements ILecturerController {

    @Autowired
    private ILecturerService lecturerService;

    @PostMapping
    @Override
    public RootEntity<DtoLecturer> saveLecturer(@RequestBody @Valid DtoLecturerIU dtoLecturerIU) {
        return ok(lecturerService.saveLecturer(dtoLecturerIU));
    }

    @GetMapping
    @Override
    public List<DtoLecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoLecturer> getLecturerById(@PathVariable(name = "id") UUID id) {
        return ok(lecturerService.getLecturerById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteLecturer(@PathVariable(name = "id") UUID id) {
        lecturerService.deleteLecturer(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoLecturer> updateLecturer(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoLecturerIU dtoLecturerIU) {
        return ok(lecturerService.updateLecturer(id, dtoLecturerIU));
    }
}
