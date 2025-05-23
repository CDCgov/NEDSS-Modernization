package gov.cdc.nbs.patient.file.summary.drr.morbidity;

import gov.cdc.nbs.patient.file.summary.drr.DocumentRequiringReview;
import gov.cdc.nbs.patient.file.summary.drr.DocumentsRequiringReviewCriteria;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class MorbidityReportRequiringReviewResolver {

  private final MorbidityReportRequiringReviewFinder reportFinder;
  private final MorbidityReportTreatmentFinder treatmentFinder;

  MorbidityReportRequiringReviewResolver(
      final MorbidityReportRequiringReviewFinder reportFinder,
      final MorbidityReportTreatmentFinder treatmentFinder
  ) {
    this.reportFinder = reportFinder;
    this.treatmentFinder = treatmentFinder;
  }

  public List<DocumentRequiringReview> resolve(final DocumentsRequiringReviewCriteria criteria) {
    if (criteria.morbidityReportScope().allowed()) {
      List<DocumentRequiringReview> reports = reportFinder.find(criteria);

      Map<Long, List<String>> treatments = treatments(criteria);

      if (treatments.isEmpty()) {
        return reports;
      }

      return reports.stream()
          .map(report -> report.withTreatments(treatments.getOrDefault(report.id(), Collections.emptyList())))
          .toList();
    }

    return Collections.emptyList();
  }

  private Map<Long, List<String>> treatments(final DocumentsRequiringReviewCriteria criteria) {
    return criteria.treatmentScope().allowed()
        ? this.treatmentFinder.find(criteria.patient(), criteria.morbidityReportScope())
        : Collections.emptyMap();
  }
}
