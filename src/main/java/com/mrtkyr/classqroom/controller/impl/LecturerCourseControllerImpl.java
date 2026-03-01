package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.ILecturerCourseController;
import com.mrtkyr.classqroom.dto.DtoLecturerCourse;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerCourseIU;
import com.mrtkyr.classqroom.entity.LecturerCourseId;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.ILecturerCourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/lecturer/course")
public class LecturerCourseControllerImpl extends RestBaseController implements ILecturerCourseController {

    @Autowired
    private ILecturerCourseService lecturerCourseService;

    @PostMapping("/save")
    @Override
    public RootEntity<DtoLecturerCourse> saveLecturerCourse(@RequestBody @Valid DtoLecturerCourseIU dtoLecturerCourseIU) {
        return ok(lecturerCourseService.saveLecturerCourse(dtoLecturerCourseIU));
    }

    @GetMapping("/list")
    @Override
    public List<DtoLecturerCourse> getAllLecturerCourses() {
        return lecturerCourseService.getAllLecturerCourses();
    }

    @GetMapping("/get/{id}")
    @Override
    public RootEntity<DtoLecturerCourse> getLecturerCourseById(@PathVariable(name = "id") LecturerCourseId id) {
        return ok(lecturerCourseService.getLecturerCourseById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteLecturerCourse(@PathVariable(name = "id") LecturerCourseId id) {
        lecturerCourseService.deleteLecturerCourse(id);
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoLecturerCourse> updateLecturerCourse(@PathVariable(name = "id") LecturerCourseId id, @RequestBody @Valid DtoLecturerCourseIU dtoLecturerCourseIU) {
        return ok(lecturerCourseService.updateLecturerCourse(id, dtoLecturerCourseIU));
    }
}
