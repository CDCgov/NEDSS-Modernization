package gov.cdc.nbs.patient;

import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.OrganizationFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.message.patient.input.AddressInput;
import gov.cdc.nbs.message.patient.input.AdministrativeInput;
import gov.cdc.nbs.message.patient.input.EmailInput;
import gov.cdc.nbs.message.patient.input.GeneralInfoInput;
import gov.cdc.nbs.message.patient.input.IdentificationInput;
import gov.cdc.nbs.message.patient.input.MortalityInput;
import gov.cdc.nbs.message.patient.input.NameInput;
import gov.cdc.nbs.message.patient.input.PhoneInput;
import gov.cdc.nbs.message.patient.input.SexAndBirthInput;
import gov.cdc.nbs.model.PatientEventResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class PatientController {
    private static final String AND = " and ";
    private static final String HAS_AUTHORITY = "hasAuthority('";
    private static final String FIND_PATIENT = HAS_AUTHORITY + Operations.FIND + "-" + BusinessObjects.PATIENT
        + "')";
    private static final String EDIT_PATIENT = HAS_AUTHORITY + Operations.EDIT + "-" + BusinessObjects.PATIENT
        + "')";

    private static final String VIEW_PATIENT = HAS_AUTHORITY + Operations.VIEW + "-" + BusinessObjects.PATIENT
        + "')";

    private static final String FIND_AND_EDIT_AND_VIEW = FIND_PATIENT + AND + EDIT_PATIENT + AND + VIEW_PATIENT;

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
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse deletePatientIdentification(@Argument Long patientId, @Argument Short entitySeqNum) {
        return patientService.deletePatientIdentification(patientId, entitySeqNum);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse deletePatientAddress(@Argument Long patientId, @Argument Short personSeqNum) {
        return patientService.deletePatientAddress(patientId, personSeqNum);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse deletePatientRace(@Argument Long patientId, @Argument String raceCd) {
        return patientService.deletePatientRace(patientId, raceCd);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse deletePatientEmail(@Argument Long patientId, @Argument Short personSeqNum) {
        return patientService.deletePatientEmail(patientId, personSeqNum);
    }


    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse deletePatientPhone(@Argument Long patientId, @Argument Short personSeqNum) {
        return patientService.deletePatientPhone(patientId, personSeqNum);
    }


}
