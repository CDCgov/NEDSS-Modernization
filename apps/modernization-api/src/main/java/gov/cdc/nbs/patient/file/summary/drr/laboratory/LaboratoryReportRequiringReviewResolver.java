package gov.cdc.nbs.patient.file.summary.drr.laboratory;

import gov.cdc.nbs.patient.events.tests.ResultedTest;
import gov.cdc.nbs.patient.events.tests.ResultedTestResolver;
import gov.cdc.nbs.patient.file.summary.drr.DocumentRequiringReview;
import gov.cdc.nbs.patient.file.summary.drr.DocumentsRequiringReviewCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class LaboratoryReportRequiringReviewResolver {

  private final LaboratoryReportRequiringReviewFinder reportFinder;
  private final ResultedTestResolver resultedTestResolver;

  LaboratoryReportRequiringReviewResolver(
      final LaboratoryReportRequiringReviewFinder reportFinder,
      final ResultedTestResolver resultedTestResolver) {
    this.reportFinder = reportFinder;
    this.resultedTestResolver = resultedTestResolver;
  }

  public List<DocumentRequiringReview> resolve(final DocumentsRequiringReviewCriteria criteria) {
    if (criteria.labReportScope().allowed()) {
      List<DocumentRequiringReview> reports = reportFinder.find(criteria);

      List<Long> identifiers = reports.stream()
          .map(DocumentRequiringReview::id)
          .toList();

      Map<Long, Collection<ResultedTest>> resultedTests = resultedTests(identifiers);


      if (resultedTests.isEmpty()) {
        return reports;
      }

      return reports.stream()
          .map(report -> report.withResultedTests(resultedTests.getOrDefault(report.id(), Collections.emptyList())))
          .toList();
    }

    return Collections.emptyList();
  }

  private Map<Long, Collection<ResultedTest>> resultedTests(final Collection<Long> identifiers) {
    return this.resultedTestResolver.resolve(identifiers);
  }
}
