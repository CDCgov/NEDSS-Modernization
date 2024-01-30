package gov.cdc.nbs.patient.documentsrequiringreview;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.GraphQLPageableMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class DocumentsRequiringReviewResolver {

  private static final Permission DOCUMENT_PERMISSION = new Permission("View", "Document");
  private static final Permission MORBIDITY_PERMISSION = new Permission("View", "ObservationMorbidityReport");
  private static final Permission LAB_PERMISSION = new Permission("View", "ObservationLabReport");

  private final GraphQLPageableMapper mapper;
  private final PatientDocumentsRequiringReviewFinder finder;
  private final PermissionScopeResolver resolver;



  DocumentsRequiringReviewResolver(
      final GraphQLPageableMapper mapper,
      final PatientDocumentsRequiringReviewFinder finder,
      final PermissionScopeResolver resolver
  ) {
    this.mapper = mapper;
    this.finder = finder;
    this.resolver = resolver;
  }


  @QueryMapping(name = "findDocumentsRequiringReviewForPatient")
  @PreAuthorize(
      "hasAuthority('FIND-PATIENT') and (hasAuthority('VIEW-DOCUMENT') or hasAuthority('VIEW-OBSERVATIONLABREPORT') or hasAuthority('VIEW-OBSERVATIONMORBIDITYREPORT'))")
  Page<DocumentRequiringReview> resolve(
      @Argument("patient") long patient,
      @Argument GraphQLPage page
  ) {
    Pageable pageable = this.mapper.from(page);
    DocumentsRequiringReviewCriteria criteria = asCriteria(patient);
    return finder.find(criteria, pageable);
  }

  private DocumentsRequiringReviewCriteria asCriteria(final long patient) {
    PermissionScope document = this.resolver.resolve(DOCUMENT_PERMISSION);
    PermissionScope morbidity = this.resolver.resolve(MORBIDITY_PERMISSION);
    PermissionScope lab = this.resolver.resolve(LAB_PERMISSION);

    return new DocumentsRequiringReviewCriteria(
        patient,
        document,
        lab,
        morbidity
    );
  }
}
