package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.ILecturerController;
import com.mrtkyr.classqroom.dto.DtoLecturer;
import com.mrtkyr.classqroom.dto.DtoLecturerIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.ILecturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/lecturer")
public class LecturerControllerImpl extends RestBaseController implements ILecturerController {

    @Autowired
    private ILecturerService lecturerService;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoLecturer> saveLecturer(@RequestBody @Valid DtoLecturerIU dtoLecturerIU) {
        return ok(lecturerService.saveLecturer(dtoLecturerIU));
    }

    @GetMapping(path = "/list")
    @Override
    public List<DtoLecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @GetMapping(path = "/get/{id}")
    @Override
    public RootEntity<DtoLecturer> getLecturerById(@PathVariable(name = "id") UUID id) {
        return ok(lecturerService.getLecturerById(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteLecturer(@PathVariable(name = "id") UUID id) {
        lecturerService.deleteLecturer(id);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoLecturer> updateLecturer(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoLecturerIU dtoLecturerIU) {
        return ok(lecturerService.updateLecturer(id, dtoLecturerIU));
    }
}
