package gov.cdc.nbs.patient.file.summary.drr;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
class DocumentRequiringReviewResolver {

  private static final Permission DOCUMENT_PERMISSION = new Permission("View", "Document");
  private static final Permission MORBIDITY_PERMISSION = new Permission("View", "ObservationMorbidityReport");
  private static final Permission LAB_PERMISSION = new Permission("View", "ObservationLabReport");
  private static final Permission INVESTIGATION_PERMISSION = new Permission("View", "Investigation");


  private final PermissionScopeResolver scopeResolver;
  private final CaseReportRequiringReviewFinder caseReportFinder;

  DocumentRequiringReviewResolver(
      final PermissionScopeResolver scopeResolver,
      final CaseReportRequiringReviewFinder caseReportFinder
  ) {
    this.scopeResolver = scopeResolver;
    this.caseReportFinder = caseReportFinder;
  }

  List<DocumentRequiringReview> resolve(final long patient) {
    DocumentsRequiringReviewCriteria criteria = asCriteria(patient);

    return caseReports(criteria);
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

  private List<DocumentRequiringReview> caseReports(final DocumentsRequiringReviewCriteria criteria) {
    return criteria.documentScope().allowed()
        ? this.caseReportFinder.find(criteria)
        : Collections.emptyList();
  }

}
