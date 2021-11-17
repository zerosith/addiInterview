package com.addi.externalcriminalrecordsvalidator.controllers;

import com.addi.externalcriminalrecordsvalidator.domain.Lead;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thirdparty/criminalvalidator")
public class CriminalRecordsValidatorController {
    @PostMapping(value = "/validateRegistry", consumes = "application/json", produces = "application/json")
    public Boolean validateLead(@RequestBody Lead candidate)  {
        return Math.random() < 0.5;
    }
}
