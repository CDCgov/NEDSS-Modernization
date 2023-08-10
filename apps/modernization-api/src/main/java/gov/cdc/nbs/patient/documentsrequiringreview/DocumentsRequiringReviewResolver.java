package gov.cdc.nbs.patient.documentsrequiringreview;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.graphql.GraphQLPage;

@Controller
public class DocumentsRequiringReviewResolver {

    private final int maxPageSize;
    private final ReviewDocumentFinder finder;

    public DocumentsRequiringReviewResolver(
            @Value("${nbs.max-page-size}") final int maxPageSize,
            final ReviewDocumentFinder finder) {
        this.maxPageSize = maxPageSize;
        this.finder = finder;
    }

    @QueryMapping(name = "findDocumentsRequiringReviewForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and (hasAuthority('VIEW-DOCUMENT') or hasAuthority('VIEW-OBSERVATIONLABREPORT') or hasAuthority('VIEW-OBSERVATIONMORBIDITYREPORT'))")
    public Page<DocumentRequiringReview> findDocumentsRequiringReviewForPatient(
            @Argument("patient") long patient,
            @Argument GraphQLPage page) {
        Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
        return finder.find(patient, pageable);
    }
}
