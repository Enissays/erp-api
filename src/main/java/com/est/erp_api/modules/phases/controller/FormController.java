package com.est.erp_api.modules.phases.controller;

import com.est.erp_api.modules.phases.services.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/forms")
public class FormController {

    @Autowired
    private FormsService formsService;

    @GetMapping("/id/{id}")
    public Object getFormById(@PathVariable String id) {
        try {
            return formsService.getLineByValue(id, "code massar");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }


}
