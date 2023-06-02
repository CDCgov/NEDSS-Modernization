package gov.cdc.nbs.patient.update;

import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.message.patient.input.*;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.patient.PatientService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class PatientUpdateController {

    private static final String AND = " and ";
    private static final String HAS_AUTHORITY = "hasAuthority('";
    private static final String FIND_PATIENT = HAS_AUTHORITY + SecurityUtil.Operations.FIND + "-" + SecurityUtil.BusinessObjects.PATIENT
            + "')";
    private static final String EDIT_PATIENT = HAS_AUTHORITY + SecurityUtil.Operations.EDIT + "-" + SecurityUtil.BusinessObjects.PATIENT
            + "')";
    private static final String VIEW_PATIENT = HAS_AUTHORITY + SecurityUtil.Operations.VIEW + "-" + SecurityUtil.BusinessObjects.PATIENT
            + "')";
    private static final String FIND_AND_EDIT_AND_VIEW = FIND_PATIENT + AND + EDIT_PATIENT + AND + VIEW_PATIENT;


    private final PatientService patientService;

    public PatientUpdateController(PatientService patientService) {
        this.patientService = patientService;
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse addPatientAddress(@Argument AddressInput input) {
        return patientService.addPatientAddress(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse addPatientEmail(@Argument EmailInput input) {
        return patientService.addPatientEmail(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse addPatientIdentification(@Argument IdentificationInput input) {
        return patientService.addPatientIdentification(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse addPatientName(@Argument NameInput input) {
        return patientService.addPatientName(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse addPatientPhone(@Argument PhoneInput input) {
        return patientService.addPatientPhone(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse addPatientRace(@Argument RaceInput input) {
        return patientService.addPatientRace(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updateAdministrative(@Argument AdministrativeInput input) {
        return patientService.updateAdministrative(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updateEthnicity(@Argument EthnicityInput input) {
        return patientService.updateEthnicity(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updateMortality(@Argument MortalityInput input) {
        return patientService.updateMortality(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientAddress(@Argument AddressInput input) {
        return patientService.updatePatientAddress(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientEmail(@Argument EmailInput input) {
        return patientService.updatePatientEmail(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientGeneralInfo(@Argument GeneralInfoInput input) {
        return patientService.updatePatientGeneralInfo(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientIdentification(@Argument IdentificationInput input) {
        return patientService.updatePatientIdentification(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientName(@Argument NameInput input) {
        return patientService.updatePatientName(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientPhone(@Argument PhoneInput input) {
        return patientService.updatePatientPhone(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientRace(@Argument RaceInput input) {
        return patientService.updatePatientRace(input);
    }

    @MutationMapping()
    @PreAuthorize(FIND_AND_EDIT_AND_VIEW)
    public PatientEventResponse updatePatientSexBirth(@Argument SexAndBirthInput input) {
        return patientService.updatePatientSexBirth(input);
    }
}
