package gov.cdc.nbs.patient.profile.redirect;

import gov.cdc.nbs.redirect.outgoing.ClassicPathResolver;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
class InvestigationOutgoingRedirector {

    private static final String INVESTIGATION =
        "/nbs/ViewFile1.do?ContextAction=InvestigationIDOnSummary&publicHealthCaseUID={investigation}";

    private final ClassicPathResolver resolver;

    InvestigationOutgoingRedirector(final ClassicPathResolver resolver) {
        this.resolver = resolver;
    }

    @ApiImplicitParam(
        name = "Authorization",
        required = true,
        allowEmptyValue = false,
        paramType = "header",
        dataTypeClass = String.class
    )
    @GetMapping("/profile/{patient}/investigation/{investigation}")
    ResponseEntity<Void> investigation(
        final HttpServletRequest request,
        final HttpServletResponse response,
        @PathVariable("patient") final long patient,
        @PathVariable("investigation") final long investigation
    ) {

        URI location = resolver.resolve(INVESTIGATION)
            .buildAndExpand(investigation)
            .toUri();

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(location)
            .build();
    }
}
