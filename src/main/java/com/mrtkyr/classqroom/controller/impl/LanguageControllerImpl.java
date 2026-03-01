package com.mrtkyr.classqroom.controller.impl;

import com.mrtkyr.classqroom.controller.ILanguageController;
import com.mrtkyr.classqroom.dto.DtoLanguage;
import com.mrtkyr.classqroom.dto.iu.DtoLanguageIU;
import com.mrtkyr.classqroom.entity.RootEntity;
import com.mrtkyr.classqroom.service.ILanguageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/language")
public class LanguageControllerImpl extends RestBaseController implements ILanguageController {

    @Autowired
    private ILanguageService languageService;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoLanguage> saveLanguage(@RequestBody @Valid DtoLanguageIU dtoLanguageIU) {
        return ok(languageService.saveLanguage(dtoLanguageIU));
    }

    @GetMapping(path = "/list")
    @Override
    public List<DtoLanguage> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @GetMapping(path = "/get/{id}")
    @Override
    public RootEntity<DtoLanguage> getLanguageById(@PathVariable(name = "id") Short id) {
        return ok(languageService.getLanguageById(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteLanguage(@PathVariable(name = "id") Short id) {
        languageService.deleteLanguage(id);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoLanguage> updateLanguage(@PathVariable(name = "id") Short id, @RequestBody @Valid DtoLanguageIU dtoLanguageIU) {
        return ok(languageService.updateLanguage(id, dtoLanguageIU));
    }
}
