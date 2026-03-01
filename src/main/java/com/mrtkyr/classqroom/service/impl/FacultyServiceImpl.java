package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoFaculty;
import com.mrtkyr.classqroom.dto.iu.DtoFacultyIU;
import com.mrtkyr.classqroom.entity.Faculty;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.FacultyRepository;
import com.mrtkyr.classqroom.service.IFacultyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements IFacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public DtoFaculty saveFaculty(DtoFacultyIU dtoFacultyIU) {
        DtoFaculty dtoFaculty = new DtoFaculty();
        Faculty faculty = new Faculty();
        BeanUtils.copyProperties(dtoFacultyIU, faculty);
        faculty = facultyRepository.save(faculty);
        BeanUtils.copyProperties(faculty, dtoFaculty);
        return dtoFaculty;
    }

    @Override
    public List<DtoFaculty> getAllFaculties() {
        List<DtoFaculty> dtoFacultyList = new ArrayList<>();
        List<Faculty> facultyList = facultyRepository.findAll();
        for (Faculty faculty : facultyList) {
            DtoFaculty dtoFaculty = new DtoFaculty();
            BeanUtils.copyProperties(faculty, dtoFaculty);
            dtoFacultyList.add(dtoFaculty);
        }
        return dtoFacultyList;
    }

    @Override
    public DtoFaculty getFacultyById(Short id) {
        DtoFaculty dtoFaculty = new DtoFaculty();
        Optional<Faculty> optFaculty = facultyRepository.findById(id);
        if (optFaculty.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Faculty faculty = optFaculty.get();
        BeanUtils.copyProperties(faculty, dtoFaculty);
        return dtoFaculty;
    }

    @Override
    public void deleteFaculty(Short id) {
        Optional<Faculty> optFaculty = facultyRepository.findById(id);
        if (optFaculty.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Faculty faculty = optFaculty.get();
        facultyRepository.delete(faculty);
    }

    @Override
    public DtoFaculty updateFaculty(Short id, DtoFacultyIU dtoFacultyIU) {
        DtoFaculty dtoFaculty = new DtoFaculty();
        Optional<Faculty> optFaculty = facultyRepository.findById(id);
        if (optFaculty.isPresent()) {
            Faculty faculty = optFaculty.get();
            faculty.setFacultyName(dtoFacultyIU.getFacultyName());
            Faculty updatedFaculty = facultyRepository.save(faculty);
            BeanUtils.copyProperties(updatedFaculty, dtoFaculty);
            return dtoFaculty;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
