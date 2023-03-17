package gov.cdc.nbs.patient;

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
import gov.cdc.nbs.graphql.filter.OrganizationFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.message.patient.input.GeneralInfoInput;
import gov.cdc.nbs.message.patient.input.MortalityInput;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.message.patient.input.SexAndBirthInput;
import gov.cdc.nbs.model.PatientEventResponse;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PatientController {
    private static final String AND = " and ";
    private static final String HAS_AUTHORITY = "hasAuthority('";
    private static final String FIND_PATIENT = HAS_AUTHORITY + Operations.FIND + "-" + BusinessObjects.PATIENT
            + "')";
    private static final String ADD_PATIENT = HAS_AUTHORITY + Operations.ADD + "-" + BusinessObjects.PATIENT + "')";
    private static final String ADD_AND_FIND_PATIENT = ADD_PATIENT + AND + FIND_PATIENT;

    private static final String EDIT_PATIENT = HAS_AUTHORITY + Operations.EDIT + "-" + BusinessObjects.PATIENT
            + "')";

    private static final String VIEW_PATIENT = HAS_AUTHORITY + Operations.VIEW + "-" + BusinessObjects.PATIENT
            + "')";
    private static final String FIND_AND_EDIT_AND_VIEW = FIND_PATIENT + AND + EDIT_PATIENT + AND + VIEW_PATIENT;
    private static final String DELETE_PATIENT = HAS_AUTHORITY + Operations.DELETE + "-" + BusinessObjects.PATIENT
            + "')";
    private final PatientService patientService;

    @QueryMapping()
    @PreAuthorize(FIND_PATIENT)
    public Page<Person> findPatientsByOrganizationFilter(@Argument OrganizationFilter filter,
            @Argument GraphQLPage page) {
        return patientService.findPatientsByOrganizationFilter(filter, page);
    }

    @QueryMapping()
    @PreAuthorize(FIND_PATIENT)
    public Page<Person> findPatientsByFilter(@Argument PatientFilter filter, @Argument GraphQLPage page) {
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
    @PreAuthorize(DELETE_PATIENT)
    public PatientEventResponse deletePatient(@Argument Long patientId) {
        return patientService.sendDeletePatientEvent(patientId);
    }

    @MutationMapping()
    @PreAuthorize(ADD_AND_FIND_PATIENT)
    public PatientEventResponse createPatient(@Argument PatientInput patient) {
        return patientService.sendCreatePatientRequest(patient);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientGeneralInfo(@Argument GeneralInfoInput input) {
        return patientService.updatePatientGeneralInfo(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientSexBirth(@Argument SexAndBirthInput input) {
        return patientService.updatePatientSexBirth(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updateMortality(@Argument MortalityInput input) {
        return patientService.updateMortality(input);
    }

}
