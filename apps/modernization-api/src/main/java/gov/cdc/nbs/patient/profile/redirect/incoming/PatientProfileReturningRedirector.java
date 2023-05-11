package gov.cdc.nbs.patient.profile.redirect.incoming;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
class PatientProfileReturningRedirector {

    private final ModernizedPatientProfileRedirectResolver resolver;

    PatientProfileReturningRedirector(final ModernizedPatientProfileRedirectResolver resolver) {
        this.resolver = resolver;
    }

    @ApiIgnore
    @GetMapping(
        value = {"/nbs/redirect/patientProfile/return", "/nbs/redirect/patientProfile/{tab}/return"}
    )
    ResponseEntity<Void> redirectPatientProfileReturn(
        final HttpServletRequest request,
        @PathVariable(required = false) final String tab
    ) {
        return resolver.fromReturnPatient(request);
    }

}
