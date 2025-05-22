package gov.cdc.nbs.patient.file.summary.drr;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
class CaseReportRequiringReviewResolver {

  private final CaseReportRequiringReviewFinder finder;

  CaseReportRequiringReviewResolver(final CaseReportRequiringReviewFinder finder) {
    this.finder = finder;
  }

  List<DocumentRequiringReview> resolve(final DocumentsRequiringReviewCriteria criteria) {
    return criteria.documentScope().allowed()
        ? this.finder.find(criteria)
        : Collections.emptyList();
  }
}
