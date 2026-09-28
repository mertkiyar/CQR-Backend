package com.mrtkyr.classqroom.service.impl;

import com.mrtkyr.classqroom.dto.DtoTerm;
import com.mrtkyr.classqroom.dto.iu.DtoTermIU;
import com.mrtkyr.classqroom.entity.Term;
import com.mrtkyr.classqroom.enums.MessageType;
import com.mrtkyr.classqroom.exception.BaseException;
import com.mrtkyr.classqroom.exception.ErrorMessage;
import com.mrtkyr.classqroom.repository.TermRepository;
import com.mrtkyr.classqroom.service.ITermService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TermServiceImpl implements ITermService {

    @Autowired
    private TermRepository termRepository;

    @Override
    public DtoTerm saveTerm(DtoTermIU dtoTermIU) {
        Term term = new Term();
        DtoTerm dtoTerm = new DtoTerm();
        BeanUtils.copyProperties(dtoTermIU, term);
        LocalDate startDate = term.getTermStartDate();
        LocalDate endDate = term.getTermEndDate();

        if (!startDate.isBefore(endDate)) {
            throw new BaseException(new ErrorMessage(MessageType.BUSINESS_RULE_VIOLATION,
                    "The End Date cannot be earlier than the Start Date!"));
        } else if (termRepository.existsByTermName(term.getTermName())) {
            throw new BaseException(new ErrorMessage(MessageType.BUSINESS_RULE_VIOLATION,
                    "This Term Name already added!"));
        } else if (termRepository.existsByAcademicYearStartAndTermType(term.getAcademicYearStart(), term.getTermType())) {
            throw new BaseException(new ErrorMessage(MessageType.BUSINESS_RULE_VIOLATION,
                    "There is already a record with this academic year and term type"));
        }

        term = termRepository.save(term);
        BeanUtils.copyProperties(term, dtoTerm);
        return dtoTerm;
    }

    @Override
    public List<DtoTerm> getAllTerms() {
        List<DtoTerm> dtoTermList = new ArrayList<>();
        List<Term> termList = termRepository.findAll(Sort.by(Sort.Direction.DESC, "termStartDate"));
        for (Term term : termList) {
            DtoTerm dtoTerm = new DtoTerm();
            BeanUtils.copyProperties(term, dtoTerm);
            dtoTermList.add(dtoTerm);
        }
        return dtoTermList;
    }

    @Override
    public DtoTerm getTermById(Short id) {
        DtoTerm dtoTerm = new DtoTerm();
        Optional<Term> optTerm = termRepository.findById(id);
        if(optTerm.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Term term = optTerm.get();
        BeanUtils.copyProperties(term, dtoTerm);
        return dtoTerm;
    }

    @Override
    public void deleteTerm(Short id) {
        Optional<Term> optTerm = termRepository.findById(id);
        if(optTerm.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Term term = optTerm.get();
        termRepository.delete(term);
    }

    @Override
    public DtoTerm updateTerm(Short id, DtoTermIU dtoTermIU) {
        DtoTerm dtoTerm = new DtoTerm();
        Optional<Term> optTerm = termRepository.findById(id);
        if(optTerm.isPresent()) {
            LocalDate startDate = dtoTermIU.getTermStartDate();
            LocalDate endDate = dtoTermIU.getTermEndDate();

            if (!startDate.isBefore(endDate)) {
                throw new BaseException(new ErrorMessage(MessageType.BUSINESS_RULE_VIOLATION,
                        "The End Date cannot be earlier than the Start Date!"));
            } else if (termRepository.existsByTermNameAndIdNot(dtoTermIU.getTermName(), id)) {
                throw new BaseException(new ErrorMessage(MessageType.BUSINESS_RULE_VIOLATION,
                        "This Term Name already added!"));
            } else if (termRepository.existsByAcademicYearStartAndTermTypeAndIdNot(dtoTermIU.getAcademicYearStart(), dtoTermIU.getTermType(), id)) {
                throw new BaseException(new ErrorMessage(MessageType.BUSINESS_RULE_VIOLATION,
                        "There is already a record with this academic year and term type"));
            }

            Term term = optTerm.get();
            term.setTermName(dtoTermIU.getTermName());
            term.setAcademicYearStart(dtoTermIU.getAcademicYearStart());
            term.setTermStartDate(dtoTermIU.getTermStartDate());
            term.setTermEndDate(dtoTermIU.getTermEndDate());
            term.setTermType(dtoTermIU.getTermType());
            Term updatedterm = termRepository.save(term);
            BeanUtils.copyProperties(updatedterm, dtoTerm);
            return dtoTerm;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
    }
}
