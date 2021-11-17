package com.external.nationalregistryvalidator.controllers;

import com.external.nationalregistryvalidator.domain.Lead;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thirdparty/nationalvalidator")
public class NationalRegistryValidatorController {
    @PostMapping(value = "/validateRegistry", consumes = "application/json", produces = "application/json")
    public Boolean validateLead(@RequestBody Lead candidate)  {
        return Boolean.TRUE;
    }
}
