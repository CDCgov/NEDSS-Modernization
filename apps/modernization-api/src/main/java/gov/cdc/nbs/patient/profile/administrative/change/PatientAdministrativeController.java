package gov.cdc.nbs.patient.profile.administrative.change;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.patient.RequestContext;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDateTime;

@Controller
class PatientAdministrativeController {

    private final Clock clock;
    private final PatientAdministrativeChangeService service;

    PatientAdministrativeController(
        final Clock clock,
        final PatientAdministrativeChangeService service
    ) {
        this.clock = clock;
        this.service = service;
    }

    @MutationMapping("updatePatientAdministrative")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
    public PatientAdministrativeChangeResult update(@Argument UpdatePatientAdministrative input) {

        NbsUserDetails user = SecurityUtil.getUserDetails();

        RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

        service.update(context, input);

        return new PatientAdministrativeChangeResult(input.patient());
    }
}
