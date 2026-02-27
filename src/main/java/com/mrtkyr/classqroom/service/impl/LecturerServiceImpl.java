package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoLecturer;
import com.mrtkyr.classqroom.dto.iu.DtoLecturerIU;
import com.mrtkyr.classqroom.entity.Lecturer;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.LecturerRepository;
import com.mrtkyr.classqroom.service.ILecturerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LecturerServiceImpl implements ILecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public DtoLecturer saveLecturer(DtoLecturerIU dtoLecturerIU) {
        Lecturer lecturer = new Lecturer();
        DtoLecturer dtoLecturer = new DtoLecturer();
        BeanUtils.copyProperties(dtoLecturerIU, lecturer);
        lecturer = lecturerRepository.save(lecturer);
        BeanUtils.copyProperties(lecturer, dtoLecturer);
        return dtoLecturer;
    }

    @Override
    public List<DtoLecturer> getAllLecturers() {
        List<DtoLecturer> dtoLecturerList = new ArrayList<>();
        List<Lecturer> lecturers = lecturerRepository.findAll();
        for (Lecturer lecturer : lecturers) {
            DtoLecturer dtoLecturer = new DtoLecturer();
            BeanUtils.copyProperties(lecturer, dtoLecturer);
            dtoLecturerList.add(dtoLecturer);
        }
        return dtoLecturerList;
    }

    @Override
    public DtoLecturer getLecturerById(UUID id) {
        DtoLecturer dtoLecturer = new DtoLecturer();
        Optional<Lecturer> optLecturer = lecturerRepository.findById(id);
        if (optLecturer.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Lecturer lecturer = optLecturer.get();
        BeanUtils.copyProperties(lecturer, dtoLecturer);
        return dtoLecturer;
    }

    @Override
    public void deleteLecturer(UUID id) {
        Optional<Lecturer> optLecturer = lecturerRepository.findById(id);
        if (optLecturer.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Lecturer lecturer = optLecturer.get();
        lecturerRepository.delete(lecturer);
    }

    @Override
    public DtoLecturer updateLecturer(UUID id, DtoLecturerIU dtoLecturerIU) {
        DtoLecturer dtoLecturer = new DtoLecturer();
        Optional<Lecturer> optLecturer = lecturerRepository.findById(id);
        if(optLecturer.isPresent()) {
            Lecturer lecturer = optLecturer.get();
            lecturer.setLecturerTitle(dtoLecturerIU.getLecturerTitle());
            lecturer.setLecturerRole(dtoLecturerIU.getLecturerRole());
            lecturer.setPhone(dtoLecturerIU.getPhone());
            lecturer.setExtPhone(dtoLecturerIU.getExtPhone());
            Lecturer updatedLecturer = lecturerRepository.save(lecturer);
            BeanUtils.copyProperties(updatedLecturer, dtoLecturer);
            return dtoLecturer;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
