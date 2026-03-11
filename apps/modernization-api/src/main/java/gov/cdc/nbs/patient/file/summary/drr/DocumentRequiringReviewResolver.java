package gov.cdc.nbs.patient.file.summary.drr;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.file.summary.drr.laboratory.LaboratoryReportRequiringReviewResolver;
import gov.cdc.nbs.patient.file.summary.drr.morbidity.MorbidityReportRequiringReviewResolver;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class DocumentRequiringReviewResolver {

  private static final Permission DOCUMENT_PERMISSION = new Permission("View", "Document");
  private static final Permission MORBIDITY_PERMISSION =
      new Permission("View", "ObservationMorbidityReport");
  private static final Permission TREATMENT_PERMISSION = new Permission("View", "Treatment");
  private static final Permission LAB_PERMISSION = new Permission("View", "ObservationLabReport");
  private static final Permission INVESTIGATION_PERMISSION =
      new Permission("View", "Investigation");

  private final PermissionScopeResolver scopeResolver;
  private final CaseReportRequiringReviewResolver caseReportResolver;
  private final MorbidityReportRequiringReviewResolver morbidityReportResolver;
  private final LaboratoryReportRequiringReviewResolver laboratoryReportRequiringReviewResolver;

  DocumentRequiringReviewResolver(
      final PermissionScopeResolver scopeResolver,
      final CaseReportRequiringReviewResolver caseReportResolver,
      final MorbidityReportRequiringReviewResolver morbidityReportResolver,
      final LaboratoryReportRequiringReviewResolver laboratoryReportRequiringReviewResolver) {
    this.scopeResolver = scopeResolver;
    this.caseReportResolver = caseReportResolver;
    this.morbidityReportResolver = morbidityReportResolver;
    this.laboratoryReportRequiringReviewResolver = laboratoryReportRequiringReviewResolver;
  }

  List<DocumentRequiringReview> resolve(final long patient) {
    DocumentsRequiringReviewCriteria criteria = asCriteria(patient);

    return Stream.of(
            caseReportResolver.resolve(criteria),
            morbidityReportResolver.resolve(criteria),
            laboratoryReportRequiringReviewResolver.resolve(criteria))
        .flatMap(Collection::stream)
        .sorted(Comparator.comparing(DocumentRequiringReview::id).reversed())
        .toList();
  }

  private DocumentsRequiringReviewCriteria asCriteria(final long patient) {
    PermissionScope document = this.scopeResolver.resolve(DOCUMENT_PERMISSION);
    PermissionScope morbidity = this.scopeResolver.resolve(MORBIDITY_PERMISSION);
    PermissionScope treatment = this.scopeResolver.resolve(TREATMENT_PERMISSION);
    PermissionScope lab = this.scopeResolver.resolve(LAB_PERMISSION);
    PermissionScope investigation = this.scopeResolver.resolve(INVESTIGATION_PERMISSION);

    return new DocumentsRequiringReviewCriteria(
        patient, document, lab, morbidity, treatment, investigation);
  }
}
