package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoDepartment;
import com.mrtkyr.classqroom.dto.iu.DtoDepartmentIU;

import java.util.List;

public interface IDepartmentService {
    DtoDepartment saveDepartment(DtoDepartmentIU dtoDepartmentIU);
    List<DtoDepartment> getAllDepartments();
    DtoDepartment getDepartmentById(Short id);
    void deleteDepartment(Short id);
    DtoDepartment updateDepartment(Short id, DtoDepartmentIU dtoDepartmentIU);
}
