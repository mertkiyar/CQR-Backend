package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IStudentController;
import com.mrtkyr.classqroom.dto.DtoStudent;
import com.mrtkyr.classqroom.dto.DtoStudentIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/student")
public class StudentControllerImpl extends RestBaseController implements IStudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoStudent> saveStudent(@RequestBody @Valid DtoStudentIU dtoStudentIU) {
        return ok(studentService.saveStudent(dtoStudentIU));
    }

    @GetMapping(path = "/list")
    @Override
    public List<DtoStudent> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "/get/{id}")
    @Override
    public RootEntity<DtoStudent> getStudentById(@PathVariable(name = "id") UUID id) {
        return ok(studentService.getStudentById(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteStudent(@PathVariable(name = "id") UUID id) {
        studentService.deleteStudent(id);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoStudent> updateStudent(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoStudentIU dtoStudentIU) {
        return ok(studentService.updateStudent(id, dtoStudentIU));
    }
}