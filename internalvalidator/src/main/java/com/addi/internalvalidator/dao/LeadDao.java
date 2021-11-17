package com.addi.internalvalidator.dao;

import com.addi.internalvalidator.domain.Lead;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class LeadDao {

    private Map<Integer, Lead> DB = new HashMap<Integer, Lead>();

    public String addLead(Lead candidate) {
        DB.put(candidate.getId(), candidate);
        return "success";
    }

    public Collection<Lead> getAllUsers() {
        Collection<Lead> list = DB.values();
        if (list.isEmpty()) {
            list.addAll(DB.values());
        }
        return list;
    }

}
