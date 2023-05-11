package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientProfileRedirector;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

@ApiIgnore
@RestController
class AddInvestigationRedirector {

    private static final String LOCATION = "/nbs/LoadSelectCondition1.do";

    private final ClassicPatientProfileRedirector redirector;

    AddInvestigationRedirector(final ClassicPatientProfileRedirector redirector) {
        this.redirector = redirector;
    }

    @PreAuthorize("hasAuthority('ADD-INVESTIGATION')")
    @GetMapping("/nbs/api/profile/{patient}/investigation")
    ResponseEntity<Void> add(@PathVariable("patient") final long patient) {

        URI location = UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("ContextAction", "AddInvestigation")
            .build()
            .toUri();

        return redirector.preparedRedirect(patient, location);
    }


}
