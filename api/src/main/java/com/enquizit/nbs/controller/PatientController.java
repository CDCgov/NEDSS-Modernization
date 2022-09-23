package com.enquizit.nbs.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.enquizit.nbs.model.patient.Patient;
import com.enquizit.nbs.model.patient.PatientFilter;
import com.enquizit.nbs.service.PatientService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @QueryMapping()
    public List<Patient> findPatientsByFilter(@Argument PatientFilter filter) {
        return patientService.findPatientsByFilter(filter);
    }
}
