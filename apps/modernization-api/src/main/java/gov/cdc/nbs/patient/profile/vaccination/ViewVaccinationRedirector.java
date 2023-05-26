package gov.cdc.nbs.patient.profile.vaccination;

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
class ViewVaccinationRedirector {

    private static final String LOCATION = "/nbs/PageAction.do";

    private final ClassicPatientProfileRedirector redirector;

    ViewVaccinationRedirector(final ClassicPatientProfileRedirector redirector) {
        this.redirector = redirector;
    }

    @PreAuthorize("hasAuthority('VIEW-INTERVENTIONVACCINERECORD')")
    @GetMapping("/nbs/api/profile/{patient}/vaccination/{identifier}")
    ResponseEntity<Void> view(
        @PathVariable("patient") final long patient,
        @PathVariable("identifier") final long identifier
    ) {

        URI location = UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("method", "viewGenericLoad")
            .queryParam("businessObjectType", "VAC")
            .queryParam("Action", "DSFilePath")
            .queryParam("actUid", identifier)
            .build()
            .toUri();

        return redirector.preparedRedirect(patient, location);
    }
}
