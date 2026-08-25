package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IStudentController;
import com.mrtkyr.classqroom.dto.DtoStudent;
import com.mrtkyr.classqroom.dto.iu.DtoStudentIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentControllerImpl extends RestBaseController implements IStudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping
    @Override
    public RootEntity<DtoStudent> saveStudent(@RequestBody @Valid DtoStudentIU dtoStudentIU) {
        return ok(studentService.saveStudent(dtoStudentIU));
    }

    @GetMapping
    @Override
    public List<DtoStudent> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoStudent> getStudentById(@PathVariable(name = "id") UUID id) {
        return ok(studentService.getStudentById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteStudent(@PathVariable(name = "id") UUID id) {
        studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoStudent> updateStudent(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoStudentIU dtoStudentIU) {
        return ok(studentService.updateStudent(id, dtoStudentIU));
    }
}