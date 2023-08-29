package gov.cdc.nbs.patient.profile.investigation.delete;

import gov.cdc.nbs.patient.profile.redirect.incoming.ModernizedPatientProfileRedirectResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@ApiIgnore
@RestController
class DeleteLegacyInvestigationRedirector {

    private static final String RETURN_TO_FILE_SUMMARY = "ReturnToFileSummary";
    private static final String CONTEXT_ACTION_PARAMETER = "ContextAction";
    private static final String DELETE_PARAMETER = "delete";
    private final RestTemplate template;
    private final ModernizedPatientProfileRedirectResolver resolver;

    DeleteLegacyInvestigationRedirector(
        @Qualifier("classic") final RestTemplate template,
        final ModernizedPatientProfileRedirectResolver resolver
    ) {
        this.template = template;
        this.resolver = resolver;
    }

    @GetMapping("/nbs/redirect/patient/investigation/delete")
    ResponseEntity<Void> deleted(
        final HttpServletRequest request,
        @RequestParam(required = false, name = CONTEXT_ACTION_PARAMETER, defaultValue = RETURN_TO_FILE_SUMMARY)
        final String action
    ) {

        deleteLegacyInvestigation(action);

        return resolver.fromReturnPatient(request);
    }

    private void deleteLegacyInvestigation(final String action) {

        String location = resolveLocation(action);
        RequestEntity<Void> request = RequestEntity
            .get(location)
            .build();

        this.template.exchange(request, Void.class);
    }

    private String resolveLocation(final String action) {
        String path = resolvePath(action);

        return UriComponentsBuilder.fromPath(path)
            .queryParam(CONTEXT_ACTION_PARAMETER, action)
            .queryParam(DELETE_PARAMETER, "true")
            .build()
            .toUriString();
    }

    private String resolvePath(final String action) {
        return Objects.equals(RETURN_TO_FILE_SUMMARY, action)
            ? "/ViewInvestigation1.do"
            : "/ViewInvestigation3.do";
    }

}
