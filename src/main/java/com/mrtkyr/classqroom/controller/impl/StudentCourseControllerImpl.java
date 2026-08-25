package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IStudentCourseController;
import com.mrtkyr.classqroom.dto.DtoStudentCourse;
import com.mrtkyr.classqroom.dto.iu.DtoStudentCourseIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.entity.StudentCourseId;
import com.mrtkyr.classqroom.service.IStudentCourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-courses")
public class StudentCourseControllerImpl extends RestBaseController implements IStudentCourseController {

    @Autowired
    private IStudentCourseService studentCourseService;

    @PostMapping
    @Override
    public RootEntity<DtoStudentCourse> saveStudentCourse(@RequestBody @Valid DtoStudentCourseIU dtoStudentCourseIU) {
        return ok(studentCourseService.saveStudentCourse(dtoStudentCourseIU));
    }

    @GetMapping
    @Override
    public List<DtoStudentCourse> getAllStudentCourses() {
        return studentCourseService.getAllStudentCourses();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoStudentCourse> getStudentCourseById(@PathVariable(name = "id") StudentCourseId id) {
        return ok(studentCourseService.getStudentCourseById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteStudentCourse(@PathVariable(name = "id") StudentCourseId id) {
        studentCourseService.deleteStudentCourse(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoStudentCourse> updateStudentCourse(@PathVariable(name = "id") StudentCourseId id, @RequestBody @Valid DtoStudentCourseIU dtoStudentCourseIU) {
        return ok(studentCourseService.updateStudentCourse(id, dtoStudentCourseIU));
    }
}
