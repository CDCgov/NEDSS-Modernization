package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.patient.documentsrequiringreview.PatientActivity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
class LaboratoryReportDetailFinder {

  Collection<DocumentRequiringReview> find(final PatientActivity.LabReport reports) {
    return Collections.emptyList();
  }

}
