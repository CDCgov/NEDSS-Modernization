package gov.cdc.nbs.patient.profile.redirect.incoming;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping(
        value = {"/nbs/redirect/patientProfile/return", "/nbs/redirect/patientProfile/{tab}/return"}
    )
    ResponseEntity<Void> redirectPatientProfileReturnPOST(
        final HttpServletRequest request,
        @PathVariable(required = false) final String tab
    ) {
        return resolver.fromReturnPatient(request);
    }

    @ApiIgnore
    @GetMapping(
        value = {"/nbs/redirect/patientProfile/return", "/nbs/redirect/patientProfile/{tab}/return"}
    )
    ResponseEntity<Void> redirectPatientProfileReturnGet(
        final HttpServletRequest request,
        @PathVariable(required = false) final String tab
    ) {
        return resolver.fromReturnPatient(request);
    }

}
