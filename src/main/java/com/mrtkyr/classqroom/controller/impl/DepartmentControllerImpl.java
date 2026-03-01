package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.IDepartmentController;
import com.mrtkyr.classqroom.dto.DtoDepartment;
import com.mrtkyr.classqroom.dto.iu.DtoDepartmentIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.IDepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/department")
public class DepartmentControllerImpl extends RestBaseController implements IDepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoDepartment> saveDepartment(@RequestBody @Valid DtoDepartmentIU dtoDepartmentIU) {
        return ok(departmentService.saveDepartment(dtoDepartmentIU));
    }

    @GetMapping(path = "/list")
    @Override
    public List<DtoDepartment> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping(path = "/get/{id}")
    @Override
    public RootEntity<DtoDepartment> getDepartmentById(@PathVariable(name = "id") Short id) {
        return ok(departmentService.getDepartmentById(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteDepartment(@PathVariable(name = "id") Short id) {
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoDepartment> updateDepartment(@PathVariable(name = "id") Short id, @RequestBody @Valid DtoDepartmentIU dtoDepartmentIU) {
        return ok(departmentService.updateDepartment(id, dtoDepartmentIU));
    }
}
