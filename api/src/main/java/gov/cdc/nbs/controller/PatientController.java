package gov.cdc.nbs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.input.PatientInput;
import gov.cdc.nbs.graphql.searchFilter.EventFilter;
import gov.cdc.nbs.graphql.searchFilter.OrganizationFilter;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import gov.cdc.nbs.service.PatientService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @QueryMapping
    public List<Person> findPatientsByEvent(@Argument EventFilter filter, @Argument GraphQLPage page) {
        return patientService.findPatientsByEvent(filter, page);
    }

    @QueryMapping()
    public List<Person> findPatientsByFilter(@Argument PatientFilter filter, @Argument GraphQLPage page) {
        return patientService.findPatientsByFilter(filter, page);
    }

    @QueryMapping()
    public List<Person> findPatientsByOrganizationFilter(@Argument OrganizationFilter filter,
            @Argument GraphQLPage page) {
        return patientService.findPatientsByOrganizationFilter(filter, page);
    }

    @QueryMapping()
    public Page<Person> findAllPatients(@Argument GraphQLPage page) {
        return patientService.findAllPatients(page);
    }

    @QueryMapping()
    public Optional<Person> findPatientById(@Argument Long id) {
        return patientService.findPatientById(id);
    }

    @MutationMapping()
    public Person createPatient(@Argument PatientInput patient) {
        return patientService.createPatient(patient);
    }

}
