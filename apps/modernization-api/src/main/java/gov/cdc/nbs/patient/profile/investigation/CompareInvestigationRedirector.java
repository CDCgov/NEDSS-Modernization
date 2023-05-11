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
class CompareInvestigationRedirector {

    private static final String LOCATION = "/nbs/ViewFile1.do";

    private final ClassicPatientProfileRedirector redirector;

    CompareInvestigationRedirector(final ClassicPatientProfileRedirector redirector) {
        this.redirector = redirector;
    }

    @PreAuthorize("hasAuthority('MERGEINVESTIGATION-INVESTIGATION')")
    @GetMapping("/nbs/api/profile/{patient}/investigation/{investigation}/compare/{other}")
    ResponseEntity<Void> view(
        @PathVariable("patient") final long patient,
        @PathVariable("investigation") final long investigation,
        @PathVariable("other") final long other

    ) {

        URI location = UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("ContextAction", "CompareInvestigations")
            .queryParam("publicHealthCaseUID0", investigation)
            .queryParam("publicHealthCaseUID1", other)
            .build()
            .toUri();

        return redirector.preparedRedirect(patient, location);
    }

}
