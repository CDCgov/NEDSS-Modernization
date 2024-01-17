package gov.cdc.nbs.patient.documentsrequiringreview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientDocumentsRequiringReviewFinder {

  private final PatientActivityRequiringReviewFinder activityFinder;

  PatientDocumentsRequiringReviewFinder(
      final PatientActivityRequiringReviewFinder activityFinder
  ) {
    this.activityFinder = activityFinder;
  }

  Page<DocumentRequiringReview> find(
      final DocumentsRequiringReviewCriteria criteria,
      final Pageable pageable
  ) {

    List<PatientActivityRequiringReview> activity = activityFinder.find(criteria);

    return (activity.isEmpty())
        ? emptyPage(pageable)
        : paged(pageable, activity);
  }

  private Page<DocumentRequiringReview> emptyPage(final Pageable pageable) {
    return new PageImpl<>(List.of(), pageable, 0);
  }

  private Page<DocumentRequiringReview> paged(
      final Pageable pageable,
      final List<PatientActivityRequiringReview> activity
  ) {

    List<DocumentRequiringReview> documents = activity.stream()
        .skip(pageable.getOffset())
        .limit(pageable.getPageSize())
        .map(this::from)
        .toList();

    return new PageImpl<>(documents, pageable, activity.size());
  }

  private DocumentRequiringReview from(final PatientActivityRequiringReview activity) {
    return new DocumentRequiringReview(
        activity.id(),
        null,
        activity.type(),
        null,
        null,
        false,
        false,
        new DocumentRequiringReview.FacilityProviders(),
        List.of()
    );
  }
}
