package gov.cdc.nbs.patient.file.summary.drr;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

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
