package gov.cdc.nbs.patient.profile.contact;

import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientProfileRedirector;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

@ApiIgnore
@RestController
class ViewContactRedirector {

    private static final String LOCATION = "/nbs/ContactTracing.do";

    private final ClassicPatientProfileRedirector redirector;

    ViewContactRedirector(final ClassicPatientProfileRedirector redirector) {
        this.redirector = redirector;
    }

    @PreAuthorize("hasAuthority('VIEW-PATIENT')")
    @GetMapping("/nbs/api/profile/{patient}/contact/{identifier}")
    ResponseEntity<Void> view(
        @PathVariable("patient") final long patient,
        @PathVariable("identifier") final long identifier,
        @RequestParam("condition") final String condition
    ) {

        URI location = UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("method", "viewContact")
            .queryParam("mode", "View")
            .queryParam("Action", "DSFilePath")
            .queryParam("contactRecordUid", identifier)
            .queryParam("DSInvestigationCondition", condition)
            .build()
            .toUri();

        return redirector.preparedRedirect(patient, location);
    }
}
