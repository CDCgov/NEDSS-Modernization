package gov.cdc.nbs.patient.file.summary.drr.morbidity;

import gov.cdc.nbs.patient.events.report.morbidity.MorbidityReportResultedTestResolver;
import gov.cdc.nbs.patient.events.report.morbidity.MorbidityReportTreatmentFinder;
import gov.cdc.nbs.patient.events.tests.ResultedTest;
import gov.cdc.nbs.patient.file.summary.drr.DocumentRequiringReview;
import gov.cdc.nbs.patient.file.summary.drr.DocumentsRequiringReviewCriteria;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MorbidityReportRequiringReviewResolver {

  private final MorbidityReportRequiringReviewFinder reportFinder;
  private final MorbidityReportTreatmentFinder treatmentFinder;
  private final MorbidityReportResultedTestResolver resultedTestResolver;

  MorbidityReportRequiringReviewResolver(
      final MorbidityReportRequiringReviewFinder reportFinder,
      final MorbidityReportTreatmentFinder treatmentFinder,
      final MorbidityReportResultedTestResolver resultedTestResolver) {
    this.reportFinder = reportFinder;
    this.treatmentFinder = treatmentFinder;
    this.resultedTestResolver = resultedTestResolver;
  }

  public List<DocumentRequiringReview> resolve(final DocumentsRequiringReviewCriteria criteria) {
    if (criteria.morbidityReportScope().allowed()) {
      List<DocumentRequiringReview> reports = reportFinder.find(criteria);

      List<Long> identifiers = reports.stream().map(DocumentRequiringReview::id).toList();

      Map<Long, Collection<String>> treatments = treatments(criteria, identifiers);

      Map<Long, Collection<ResultedTest>> resultedTests = resultedTests(identifiers);

      if (treatments.isEmpty() && resultedTests.isEmpty()) {
        return reports;
      }

      return reports.stream()
          .map(
              report ->
                  report
                      .withTreatments(treatments.getOrDefault(report.id(), Collections.emptyList()))
                      .withResultedTests(
                          resultedTests.getOrDefault(report.id(), Collections.emptyList())))
          .toList();
    }

    return Collections.emptyList();
  }

  private Map<Long, Collection<String>> treatments(
      final DocumentsRequiringReviewCriteria criteria, final Collection<Long> identifiers) {
    return criteria.treatmentScope().allowed()
        ? this.treatmentFinder.find(identifiers)
        : Collections.emptyMap();
  }

  private Map<Long, Collection<ResultedTest>> resultedTests(final Collection<Long> identifiers) {
    return this.resultedTestResolver.resolve(identifiers);
  }
}
