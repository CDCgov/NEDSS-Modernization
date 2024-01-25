package gov.cdc.nbs.patient.documentsrequiringreview;

import gov.cdc.nbs.patient.documentsrequiringreview.detail.DocumentRequiringReviewDetailResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientDocumentsRequiringReviewFinder {

  private final PatientActivityRequiringReviewFinder finder;
  private final DocumentRequiringReviewDetailResolver resolver;

  PatientDocumentsRequiringReviewFinder(
      final PatientActivityRequiringReviewFinder finder,
      final DocumentRequiringReviewDetailResolver resolver
  ) {
    this.finder = finder;
    this.resolver = resolver;
  }

  Page<DocumentRequiringReview> find(
      final DocumentsRequiringReviewCriteria criteria,
      final Pageable pageable
  ) {

    PatientActivity activity = finder.find(criteria, pageable);

    return (activity.isEmpty())
        ? emptyPage(pageable)
        : paged(pageable, activity);
  }

  private Page<DocumentRequiringReview> emptyPage(final Pageable pageable) {
    return new PageImpl<>(List.of(), pageable, 0);
  }

  private Page<DocumentRequiringReview> paged(
      final Pageable pageable,
      final PatientActivity activity
  ) {

    List<DocumentRequiringReview> documents = resolver.resolve(activity);

    return new PageImpl<>(documents, pageable, activity.total());
  }

}
