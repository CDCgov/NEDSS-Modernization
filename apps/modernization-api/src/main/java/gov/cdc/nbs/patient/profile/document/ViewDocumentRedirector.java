package gov.cdc.nbs.patient.profile.document;

import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientProfileRedirector;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Hidden
@RestController
class ViewDocumentRedirector {

    private static final String LOCATION = "/nbs/ViewFile1.do";

    private final ClassicPatientProfileRedirector redirector;

    ViewDocumentRedirector(final ClassicPatientProfileRedirector redirector) {
        this.redirector = redirector;
    }

    @PreAuthorize("hasAuthority('VIEW-DOCUMENT')")
    @GetMapping("/nbs/api/profile/{patient}/document/{identifier}")
    ResponseEntity<Void> view(
        @PathVariable final long patient,
        @PathVariable final long identifier
    ) {


        URI location = UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("ContextAction", "DocumentIDOnEvents")
            .queryParam("nbsDocumentUid", identifier)
            .build()
            .toUri();

        return redirector.preparedRedirect(patient, location);
    }
}
