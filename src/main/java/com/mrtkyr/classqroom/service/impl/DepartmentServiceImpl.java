package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoDepartment;
import com.mrtkyr.classqroom.dto.iu.DtoDepartmentIU;
import com.mrtkyr.classqroom.entity.Department;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.DepartmentRepository;
import com.mrtkyr.classqroom.service.IDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DtoDepartment saveDepartment(DtoDepartmentIU dtoDepartmentIU) {
        DtoDepartment dtoDepartment = new DtoDepartment();
        Department department = new Department();
        BeanUtils.copyProperties(dtoDepartmentIU, department);
        department = departmentRepository.save(department);
        BeanUtils.copyProperties(department, dtoDepartment);
        return dtoDepartment;
    }

    @Override
    public List<DtoDepartment> getAllDepartments() {
        List<DtoDepartment> dtoDepartmentList = new ArrayList<>();
        List<Department> departmentList = departmentRepository.findAll();
        for (Department department : departmentList) {
            DtoDepartment dtoDepartment = new DtoDepartment();
            BeanUtils.copyProperties(department, dtoDepartment);
            dtoDepartmentList.add(dtoDepartment);
        }
        return dtoDepartmentList;
    }

    @Override
    public DtoDepartment getDepartmentById(Short id) {
        DtoDepartment dtoDepartment = new DtoDepartment();
        Optional<Department> optDepartment = departmentRepository.findById(id);
        if (optDepartment.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Department department = optDepartment.get();
        BeanUtils.copyProperties(department, dtoDepartment);
        return dtoDepartment;
    }

    @Override
    public void deleteDepartment(Short id) {
        Optional<Department> optDepartment = departmentRepository.findById(id);
        if (optDepartment.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Department department = optDepartment.get();
        departmentRepository.delete(department);
    }

    @Override
    public DtoDepartment updateDepartment(Short id, DtoDepartmentIU dtoDepartmentIU) {
        DtoDepartment dtoDepartment = new DtoDepartment();
        Optional<Department> optDepartment = departmentRepository.findById(id);
        if (optDepartment.isPresent()) {
            Department department = optDepartment.get();
            department.setDepartmentName(dtoDepartmentIU.getDepartmentName());
            department.setDepartmentCode(dtoDepartmentIU.getDepartmentCode());
            Department updatedDepartment = departmentRepository.save(department);
            BeanUtils.copyProperties(updatedDepartment, dtoDepartment);
            return dtoDepartment;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
