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
@RequestMapping("/departments")
public class DepartmentControllerImpl extends RestBaseController implements IDepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @PostMapping
    @Override
    public RootEntity<DtoDepartment> saveDepartment(@RequestBody @Valid DtoDepartmentIU dtoDepartmentIU) {
        return ok(departmentService.saveDepartment(dtoDepartmentIU));
    }

    @GetMapping
    @Override
    public List<DtoDepartment> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoDepartment> getDepartmentById(@PathVariable(name = "id") Short id) {
        return ok(departmentService.getDepartmentById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteDepartment(@PathVariable(name = "id") Short id) {
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoDepartment> updateDepartment(@PathVariable(name = "id") Short id, @RequestBody @Valid DtoDepartmentIU dtoDepartmentIU) {
        return ok(departmentService.updateDepartment(id, dtoDepartmentIU));
    }
}
