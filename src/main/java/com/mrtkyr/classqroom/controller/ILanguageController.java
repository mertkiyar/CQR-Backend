package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoLanguage;
import com.mrtkyr.classqroom.dto.iu.DtoLanguageIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;

public interface ILanguageController {
    RootEntity<DtoLanguage> saveLanguage(DtoLanguageIU dtoLanguageIU);
    List<DtoLanguage> getAllLanguages();
    RootEntity<DtoLanguage> getLanguageById(Short id);
    void deleteLanguage(Short id);
    RootEntity<DtoLanguage> updateLanguage(Short id, DtoLanguageIU dtoLanguageIU);
}
