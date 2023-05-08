package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
class PatientProfileReturningRedirector {

    @ApiIgnore
    @GetMapping(
        value = {"/nbs/redirect/patientProfile/return", "/nbs/redirect/patientProfile/{tab}/return"}
    )
    ResponseEntity<Void> redirectPatientProfileReturn(
        final HttpServletRequest request,
        @PathVariable(required = false) final String tab
    ) {
        String returning = ReturningPatientCookie.resolve(request.getCookies())
            .map(ReturningPatientCookie::patient)
            .orElse(null);

        URI location = UriComponentsBuilder.fromPath("/")
            .path("patient-profile/{identifier}")
            .buildAndExpand(returning)
            .toUri();

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(location)
            .headers(ReturningPatientCookie.empty()::apply)
            .build();
    }

}
