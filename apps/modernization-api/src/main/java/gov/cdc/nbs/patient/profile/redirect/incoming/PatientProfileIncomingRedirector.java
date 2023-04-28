package gov.cdc.nbs.patient.profile.redirect.incoming;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
class PatientProfileIncomingRedirector {

    private static final String PATIENT_PARENT_ID = "MPRUid";
    private static final String PATIENT_ID = "uid";
    

    /**
     * Intercepts patient-profile from legacy page and re-routes/forwards the patient-profile to the modernized patient
     * profile page
     */
    @ApiIgnore
    @PostMapping("/nbs/redirect/patientProfile")
    ResponseEntity<Void> redirectPatientProfile(final HttpServletRequest request) {

        String patient = resolvePatientIdentifier(request);


        URI location = UriComponentsBuilder.fromPath("/")
            .path("patient-profile/{identifier}")
            .buildAndExpand(patient)
            .toUri();

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(location)
            .build();
    }



    private String resolvePatientIdentifier(final HttpServletRequest request) {
        String parent = request.getParameter(PATIENT_PARENT_ID);

        if (parent == null) {
            return request.getParameter(PATIENT_ID);
        }
        return parent;

    }
}
