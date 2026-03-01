package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.ICourseController;
import com.mrtkyr.classqroom.dto.DtoCourse;
import com.mrtkyr.classqroom.dto.iu.DtoCourseIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.ICourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/course")
public class CourseControllerImpl extends RestBaseController implements ICourseController {

    @Autowired
    private ICourseService courseService;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoCourse> saveCourse(@RequestBody @Valid DtoCourseIU dtoCourseIU) {
        return ok(courseService.saveCourse(dtoCourseIU));
    }

    @GetMapping(path = "/list")
    @Override
    public List<DtoCourse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/get/{id}")
    @Override
    public RootEntity<DtoCourse> getCourseById(@PathVariable(name = "id") UUID id) {
        return ok(courseService.getCourseById(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteCourse(@PathVariable(name = "id") UUID id) {
        courseService.deleteCourse(id);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoCourse> updateCouse(@PathVariable(name = "id") UUID id, @RequestBody @Valid DtoCourseIU dtoCourseIU) {
        return ok(courseService.updateCourse(id, dtoCourseIU));
    }
}
