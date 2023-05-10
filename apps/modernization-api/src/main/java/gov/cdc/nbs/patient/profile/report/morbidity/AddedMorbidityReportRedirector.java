package gov.cdc.nbs.patient.profile.report.morbidity;

import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
class AddedMorbidityReportRedirector {

    private static final String LOCATION = "/AddObservationMorb2.do";

    private final RestTemplate template;

    AddedMorbidityReportRedirector(
        @Qualifier("classic") final RestTemplate template
    ) {
        this.template = template;
    }

    @PostMapping(
        path = "/nbs/redirect/patient/report/morbidity/added",
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    ResponseEntity<Void> added(
        final HttpServletRequest request,
        @RequestParam final MultiValueMap<String, String> data
    ) {

        add(data);

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

    private void add(final MultiValueMap<String, String> data) {
        RequestEntity<MultiValueMap<String, String>> request = RequestEntity
            .post(LOCATION)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(data);

        this.template.exchange(request, Void.class);
    }
}
