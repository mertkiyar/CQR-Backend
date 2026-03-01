package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoLanguage;
import com.mrtkyr.classqroom.dto.iu.DtoLanguageIU;

import java.util.List;

public interface ILanguageService {
    DtoLanguage saveLanguage(DtoLanguageIU dtoLanguageIU);
    List<DtoLanguage> getAllLanguages();
    DtoLanguage getLanguageById(Short id);
    void deleteLanguage(Short id);
    DtoLanguage updateLanguage(Short id, DtoLanguageIU dtoLanguageIU);
}
