package com.addi.internalvalidator.service;

import com.addi.internalvalidator.dao.LeadDao;
import com.addi.internalvalidator.domain.Lead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class InternalValidatorService {
    private static Integer MIN = 0;
    private static Integer MAX = 100;

    private static final Logger logger = LogManager.getLogger(InternalValidatorService.class);


    @Autowired
    private Validator validator;

    @Autowired
    private LeadDao leadDao;

    private void validateData(Lead candidate){
        Set<ConstraintViolation<Lead>> violations = validator.validate(candidate);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Lead> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

    }

    public Lead validateLead(Lead candidate) throws CloneNotSupportedException {
        validateData(candidate);
        Integer score = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
        Lead result = (Lead) candidate.clone();

        if (score >  60){
            result.setAccepted(Boolean.TRUE);
            logger.debug("lead Accepted: "+ result.getId());
            leadDao.addLead(result);

        }else{
            candidate.setAccepted(Boolean.FALSE);
            logger.debug("lead not accepted: " + result.getId());
        }
        return  result;
    }

    public Object getAuthorizedLeads() {
        return leadDao.getAllUsers();
    }
}
