package gov.cdc.nbs.patient.file.summary.drr;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Component
class DocumentRequiringReviewResolver {

  private static final Permission DOCUMENT_PERMISSION = new Permission("View", "Document");
  private static final Permission MORBIDITY_PERMISSION = new Permission("View", "ObservationMorbidityReport");
  private static final Permission LAB_PERMISSION = new Permission("View", "ObservationLabReport");
  private static final Permission INVESTIGATION_PERMISSION = new Permission("View", "Investigation");


  private final PermissionScopeResolver scopeResolver;
  private final CaseReportRequiringReviewResolver caseReportResolver;
  private final MorbidityReportRequiringReviewResolver morbidityReportResolver;

  DocumentRequiringReviewResolver(
      final PermissionScopeResolver scopeResolver,
      final CaseReportRequiringReviewResolver caseReportResolver,
      final MorbidityReportRequiringReviewResolver morbidityReportResolver
  ) {
    this.scopeResolver = scopeResolver;
    this.caseReportResolver = caseReportResolver;
    this.morbidityReportResolver = morbidityReportResolver;
  }

  List<DocumentRequiringReview> resolve(final long patient) {
    DocumentsRequiringReviewCriteria criteria = asCriteria(patient);

    return Stream.of(
            morbidityReportResolver.resolve(criteria),
            caseReportResolver.resolve(criteria)
        ).flatMap(Collection::stream)
        .toList();
  }

  private DocumentsRequiringReviewCriteria asCriteria(final long patient) {
    PermissionScope document = this.scopeResolver.resolve(DOCUMENT_PERMISSION);
    PermissionScope morbidity = this.scopeResolver.resolve(MORBIDITY_PERMISSION);
    PermissionScope lab = this.scopeResolver.resolve(LAB_PERMISSION);
    PermissionScope investigation = this.scopeResolver.resolve(INVESTIGATION_PERMISSION);

    return new DocumentsRequiringReviewCriteria(
        patient,
        document,
        lab,
        morbidity,
        investigation
    );
  }

}
