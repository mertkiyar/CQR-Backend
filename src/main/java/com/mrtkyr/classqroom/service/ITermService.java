package com.mrtkyr.classqroom.service;

import com.mrtkyr.classqroom.dto.DtoTerm;
import com.mrtkyr.classqroom.dto.iu.DtoTermIU;

import java.util.List;

public interface ITermService {
    DtoTerm saveTerm(DtoTermIU dtoTermIU);
    List<DtoTerm> getAllTerms();
    DtoTerm getTermById(Short id);
    void deleteTerm(Short id);
    DtoTerm updateTerm(Short id, DtoTermIU dtoTermIU);
}
