package gov.cdc.nbs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.PatientFilter;
import gov.cdc.nbs.service.PatientService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @QueryMapping()
    public List<Person> findPatientsByFilter(@Argument PatientFilter filter) {
        return patientService.findPatientsByFilter(filter);
    }

    @QueryMapping()
    public Page<Person> findAllPatients(@Argument GraphQLPage page) {
        return patientService.findAllPatients(page);
    }

    @QueryMapping()
    public Optional<Person> findPatientById(@Argument Long id) {
        return patientService.findPatientById(id);
    }
}
