package com.addi.internalvalidator.controllers;

import com.addi.internalvalidator.domain.Lead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.addi.internalvalidator.service.InternalValidatorService;

@RestController
@RequestMapping("/internalvalidator")
public class InternalValidatorController {

   @Autowired
   InternalValidatorService internalValidator;

    @PostMapping(value = "/validateLead", consumes = "application/json", produces = "application/json")
    public Lead validateLead(@RequestBody Lead candidate)  {
        try {
            return internalValidator.validateLead(candidate);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping (value = "/authorizedLeads", consumes = "application/json", produces = "application/json")
    public Object getAuthorizedLeads(){
        return internalValidator.getAuthorizedLeads();
    }
}
