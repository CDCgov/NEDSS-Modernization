package gov.cdc.nbs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
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
    private final String FIND_PATIENT = "hasAuthority('" + Operations.FIND + "-" + BusinessObjects.PATIENT + "')";
    private final String ADD_PATIENT = "hasAuthority('" + Operations.ADD + "-" + BusinessObjects.PATIENT + "')";
    private final String ADD_AND_FIND_PATIENT = ADD_PATIENT + " and " + FIND_PATIENT;

    @QueryMapping
    @PreAuthorize(FIND_PATIENT)
    public Page<Person> findPatientsByEvent(@Argument EventFilter filter, @Argument GraphQLPage page) {
        return patientService.findPatientsByEvent(filter, page);
    }

    @QueryMapping()
    @PreAuthorize(FIND_PATIENT)
    public List<Person> findPatientsByOrganizationFilter(@Argument OrganizationFilter filter,
            @Argument GraphQLPage page) {
        return patientService.findPatientsByOrganizationFilter(filter, page);
    }

    @QueryMapping()
    @PreAuthorize(FIND_PATIENT)
    public List<Person> findPatientsByFilter(@Argument PatientFilter filter, @Argument GraphQLPage page) {
        return patientService.findPatientsByFilter(filter, page);
    }

    @QueryMapping()
    @PreAuthorize(FIND_PATIENT)
    public Page<Person> findAllPatients(@Argument GraphQLPage page) {
        return patientService.findAllPatients(page);
    }

    @QueryMapping()
    @PreAuthorize(FIND_PATIENT)
    public Optional<Person> findPatientById(@Argument Long id) {
        return patientService.findPatientById(id);
    }

    @MutationMapping()
    @PreAuthorize(ADD_AND_FIND_PATIENT)
    public Person createPatient(@Argument PatientInput patient) {
        return patientService.createPatient(patient);
    }

}
