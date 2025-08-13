package gov.cdc.nbs.patient.file.events.record.birth.redirect;

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
class SubmitBirthRecordRedirector {

    private static final String LOCATION = "/nbs/PageAction.do";

    private final ClassicPatientProfileRedirector redirector;

    SubmitBirthRecordRedirector(final ClassicPatientProfileRedirector redirector) {
        this.redirector = redirector;
    }

    @PreAuthorize("hasAuthority('ADD-BIRTHRECORD')")
    @GetMapping("/nbs/api/patients/{patient}/records/birth/add")
    ResponseEntity<Void> view(
        @PathVariable final long patient
    ) {

        URI location = UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("method", "createGenericLoad")
            .queryParam("businessObjectType", "BIR")
            .queryParam("Action", "DSFilePath")
            .build()
            .toUri();

        return redirector.preparedRedirect(patient, location);
    }
}
