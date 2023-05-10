package gov.cdc.nbs.patient.profile.report.morbidity;

import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientProfilePreparer;
import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientSearchPreparer;
import org.springframework.http.HttpStatus;
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
class ViewMorbidityReportRedirector {

    private static final String LOCATION = "/nbs/ViewFile1.do";

    private final ClassicPatientSearchPreparer searchPreparer;
    private final ClassicPatientProfilePreparer profilePreparer;

    ViewMorbidityReportRedirector(
        final ClassicPatientSearchPreparer searchPreparer,
        final ClassicPatientProfilePreparer profilePreparer
    ) {
        this.searchPreparer = searchPreparer;
        this.profilePreparer = profilePreparer;
    }

    @PreAuthorize("hasAuthority('VIEW-OBSERVATIONMORBIDITYREPORT')")
    @GetMapping("/nbs/api/profile/{patient}/report/morbidity/{identifier}")
    ResponseEntity<Void> view(
        @PathVariable("patient") final long patient,
        @PathVariable("identifier") final long identifier
    ) {
        prepare(patient);

        URI location = UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("ContextAction", "ObservationMorbIDOnSummary")
            .queryParam("observationUID", identifier)
            .build()
            .toUri();

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .location(location)
            .headers(new ReturningPatientCookie(patient)::apply)
            .build();
    }

    private void prepare(final long patient) {
        searchPreparer.prepare();
        profilePreparer.prepare(patient);
    }
}
