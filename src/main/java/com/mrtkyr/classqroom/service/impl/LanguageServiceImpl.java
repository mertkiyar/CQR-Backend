package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoLanguage;
import com.mrtkyr.classqroom.dto.iu.DtoLanguageIU;
import com.mrtkyr.classqroom.entity.Language;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.LanguageRepository;
import com.mrtkyr.classqroom.service.ILanguageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements ILanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public DtoLanguage saveLanguage(DtoLanguageIU dtoLanguageIU) {
        Language language = new Language();
        DtoLanguage dtoLanguage = new DtoLanguage();
        BeanUtils.copyProperties(dtoLanguageIU, language);
        language = languageRepository.save(language);
        BeanUtils.copyProperties(language, dtoLanguage);
        return dtoLanguage;
    }

    @Override
    public List<DtoLanguage> getAllLanguages() {
        List<DtoLanguage> dtoLanguageList = new ArrayList<>();
        List<Language> languageList = languageRepository.findAll();
        for (Language language : languageList) {
            DtoLanguage dtoLanguage = new DtoLanguage();
            BeanUtils.copyProperties(language, dtoLanguage);
            dtoLanguageList.add(dtoLanguage);
        }
        return dtoLanguageList;
    }

    @Override
    public DtoLanguage getLanguageById(Short id) {
        DtoLanguage dtoLanguage = new DtoLanguage();
        Optional<Language> optLanguage = languageRepository.findById(id);
        if(optLanguage.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Language language = optLanguage.get();
        BeanUtils.copyProperties(language, dtoLanguage);
        return dtoLanguage;
    }

    @Override
    public void deleteLanguage(Short id) {
        Optional<Language> optLanguage = languageRepository.findById(id);
        if(optLanguage.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Language language = optLanguage.get();
        languageRepository.delete(language);
    }

    @Override
    public DtoLanguage updateLanguage(Short id, DtoLanguageIU dtoLanguageIU) {
        DtoLanguage dtoLanguage = new DtoLanguage();
        Optional<Language> optLanguage = languageRepository.findById(id);
        if(optLanguage.isPresent()) {
            Language language = optLanguage.get();
            language.setLanguageName(dtoLanguageIU.getLanguageName());
            Language updatedLanguage = languageRepository.save(language);
            BeanUtils.copyProperties(updatedLanguage, dtoLanguage);
            return dtoLanguage;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
