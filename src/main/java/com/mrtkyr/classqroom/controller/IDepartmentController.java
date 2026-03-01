package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoDepartment;
import com.mrtkyr.classqroom.dto.iu.DtoDepartmentIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;

public interface IDepartmentController {
    RootEntity<DtoDepartment> saveDepartment(DtoDepartmentIU dtoDepartmentIU);
    List<DtoDepartment> getAllDepartments();
    RootEntity<DtoDepartment> getDepartmentById(Short id);
    void deleteDepartment(Short id);
    RootEntity<DtoDepartment> updateDepartment(Short id, DtoDepartmentIU dtoDepartmentIU);
}
