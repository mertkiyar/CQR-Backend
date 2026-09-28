package com.mrtkyr.classqroom.controller;

import com.mrtkyr.classqroom.dto.DtoTerm;
import com.mrtkyr.classqroom.dto.iu.DtoTermIU;
import com.mrtkyr.classqroom.entity.RootEntity;

import java.util.List;

public interface ITermController {
    RootEntity<DtoTerm> saveTerm(DtoTermIU dtoTermIU);
    List<DtoTerm> getAllTerms();
    RootEntity<DtoTerm> getTermById(Short id);
    void deleteTerm(Short id);
    RootEntity<DtoTerm> updateTerm(Short id, DtoTermIU dtoTermIU);
}
