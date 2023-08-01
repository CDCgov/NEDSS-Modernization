package gov.cdc.nbs.patient.document;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
class PatientDocumentByPatientResolver {

    private final int maxPageSize;
    private final PatientDocumentFinder finder;
    private final SecurityService securityService;

    PatientDocumentByPatientResolver(
            @Value("${nbs.max-page-size}") final int maxPageSize,
            final PatientDocumentFinder finder,
            final SecurityService securityService) {
        this.maxPageSize = maxPageSize;
        this.finder = finder;
        this.securityService = securityService;
    }

    @QueryMapping(name = "findDocumentsForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-DOCUMENT')")
    Page<PatientDocument> find(
            @Argument("patient") final long patient,
            @Argument final GraphQLPage page) {
        Set<Long> userOids = securityService.getCurrentUserProgramAreaJurisdictionOids();
        Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
        return this.finder.find(
                patient,
                userOids,
                pageable);
    }

    @SchemaMapping("documents")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-DOCUMENT')")
    List<PatientDocument> resolve(final Person patient) {
        Set<Long> userOids = securityService.getCurrentUserProgramAreaJurisdictionOids();
        return this.finder.find(patient.getId(), userOids);
    }
}
