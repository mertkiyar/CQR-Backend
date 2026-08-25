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
@RequestMapping("/languages")
public class LanguageControllerImpl extends RestBaseController implements ILanguageController {

    @Autowired
    private ILanguageService languageService;

    @PostMapping
    @Override
    public RootEntity<DtoLanguage> saveLanguage(@RequestBody @Valid DtoLanguageIU dtoLanguageIU) {
        return ok(languageService.saveLanguage(dtoLanguageIU));
    }

    @GetMapping
    @Override
    public List<DtoLanguage> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoLanguage> getLanguageById(@PathVariable(name = "id") Short id) {
        return ok(languageService.getLanguageById(id));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteLanguage(@PathVariable(name = "id") Short id) {
        languageService.deleteLanguage(id);
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<DtoLanguage> updateLanguage(@PathVariable(name = "id") Short id, @RequestBody @Valid DtoLanguageIU dtoLanguageIU) {
        return ok(languageService.updateLanguage(id, dtoLanguageIU));
    }
}
