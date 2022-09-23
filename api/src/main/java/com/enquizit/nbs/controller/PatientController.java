package com.enquizit.nbs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.enquizit.nbs.model.graphql.GraphQLPage;
import com.enquizit.nbs.model.graphql.PatientFilter;
import com.enquizit.nbs.model.patient.Patient;
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

    @QueryMapping()
    public Page<Patient> findAllPatients(@Argument GraphQLPage page) {
        return patientService.findAllPatients(page);
    }

    @QueryMapping()
    public Optional<Patient> findPatientById(@Argument Long id) {
        return patientService.findPatientById(id);
    }
}
