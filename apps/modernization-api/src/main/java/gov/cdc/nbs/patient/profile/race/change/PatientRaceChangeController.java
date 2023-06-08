package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.message.patient.input.RaceInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientRaceChangeController {

    @MutationMapping("addPatientRace")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
    PatientRaceChangeResult add(@Argument RaceInput input) {
        return new PatientRaceChangeResult(input.getPatientId());
    }

    @MutationMapping("updatePatientRace")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
    PatientRaceChangeResult update(@Argument RaceInput input) {
        return new PatientRaceChangeResult(input.getPatientId());
    }

    @MutationMapping("deletePatientRace")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
    PatientRaceChangeResult delete(@Argument Long patientId, @Argument String raceCd) {
        return new PatientRaceChangeResult(patientId);
    }

}
