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
class ViewInvestigationRedirector {

    private static final String LOCATION = "/nbs/ViewFile1.do";

    private final ClassicPatientProfileRedirector redirector;

    ViewInvestigationRedirector(final ClassicPatientProfileRedirector redirector) {
        this.redirector = redirector;
    }

    @PreAuthorize("hasAuthority('VIEW-INVESTIGATION')")
    @GetMapping("/nbs/api/profile/{patient}/investigation/{investigation}")
    ResponseEntity<Void> view(
        @PathVariable("patient") final long patient,
        @PathVariable("investigation") final long investigation
    ) {

        URI location = UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("ContextAction", "InvestigationIDOnSummary")
            .queryParam("publicHealthCaseUID", investigation)
            .build()
            .toUri();

        return redirector.preparedRedirect(patient, location);
    }


}
