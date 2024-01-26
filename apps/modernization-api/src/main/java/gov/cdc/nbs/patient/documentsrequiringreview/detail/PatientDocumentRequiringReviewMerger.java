package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;

import java.util.List;

public class PatientDocumentRequiringReviewMerger {

  public DocumentRequiringReview merge(final DocumentRequiringReview current, final DocumentRequiringReview next) {

    List<DocumentRequiringReview.Description> descriptions = CollectionMerge.merge(
            current.descriptions(),
            next.descriptions()
        )
        .toList();

    return new DocumentRequiringReview(
        current.id(),
        current.localId(),
        current.type(),
        current.eventDate(),
        current.dateReceived(),
        current.isElectronic(),
        current.isUpdate(),
        current.facilityProviders(),
        descriptions
    );
  }

}
