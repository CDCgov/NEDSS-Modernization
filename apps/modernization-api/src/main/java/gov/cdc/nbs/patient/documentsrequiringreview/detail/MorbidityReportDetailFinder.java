package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.patient.documentsrequiringreview.PatientActivity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
class MorbidityReportDetailFinder {

  Collection<DocumentRequiringReview> find(final PatientActivity.MorbidityReport reports) {
    return Collections.emptyList();
  }

}
