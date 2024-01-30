package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.patient.documentsrequiringreview.PatientActivityRequiringReview;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentRequiringReviewDetailResolver {

  private final CaseReportDetailFinder caseReportDetailFinder;
  private final LaboratoryReportDetailFinder laboratoryReportDetailFinder;
  private final MorbidityReportDetailFinder morbidityReportDetailFinder;

  DocumentRequiringReviewDetailResolver(
      final CaseReportDetailFinder caseReportDetailFinder,
      final LaboratoryReportDetailFinder laboratoryReportDetailFinder,
      final MorbidityReportDetailFinder morbidityReportDetailFinder
  ) {
    this.caseReportDetailFinder = caseReportDetailFinder;
    this.laboratoryReportDetailFinder = laboratoryReportDetailFinder;
    this.morbidityReportDetailFinder = morbidityReportDetailFinder;
  }

  public List<DocumentRequiringReview> resolve(final PatientActivityRequiringReview activity) {
    List<DocumentRequiringReview> details = new ArrayList<>(caseReportDetailFinder.find(activity.cases()));
    details.addAll(laboratoryReportDetailFinder.find(activity.labs()));
    details.addAll(morbidityReportDetailFinder.find(activity.morbidity()));
    return details;
  }

}
