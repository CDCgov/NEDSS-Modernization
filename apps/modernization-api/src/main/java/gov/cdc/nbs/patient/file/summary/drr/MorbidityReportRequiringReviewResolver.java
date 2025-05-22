package gov.cdc.nbs.patient.file.summary.drr;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
class MorbidityReportRequiringReviewResolver {

  private final MorbidityReportRequiringReviewFinder finder;

  MorbidityReportRequiringReviewResolver(final MorbidityReportRequiringReviewFinder finder) {
    this.finder = finder;
  }

  List<DocumentRequiringReview> resolve(final DocumentsRequiringReviewCriteria criteria) {
    if (criteria.morbidityReportScope().allowed()) {
      return finder.find(criteria);
    }

    return Collections.emptyList();
  }
}
