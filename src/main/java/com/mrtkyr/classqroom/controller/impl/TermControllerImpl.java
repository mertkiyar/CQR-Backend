package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.ITermController;
import com.mrtkyr.classqroom.dto.DtoTerm;
import com.mrtkyr.classqroom.dto.iu.DtoTermIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.ITermService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terms")
public class TermControllerImpl extends RestBaseController implements ITermController {

    @Autowired
    private ITermService termService;

    @PostMapping
    @Override
    public RootEntity<DtoTerm> saveTerm(@RequestBody @Valid DtoTermIU dtoTermIU) {
        return ok(termService.saveTerm(dtoTermIU));
    }

    @GetMapping
    @Override
    public List<DtoTerm> getAllTerms() {
        return termService.getAllTerms();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoTerm> getTermById(@PathVariable(name = "id") Short id) {
        return ok(termService.getTermById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteTerm(@PathVariable(name = "id") Short id) {
        termService.deleteTerm(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoTerm> updateTerm(@PathVariable(name = "id") Short id, @RequestBody @Valid DtoTermIU dtoTermIU) {
        return ok(termService.updateTerm(id, dtoTermIU));
    }
}
