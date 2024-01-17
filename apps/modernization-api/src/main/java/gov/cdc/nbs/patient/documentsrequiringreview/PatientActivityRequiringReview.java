package gov.cdc.nbs.patient.documentsrequiringreview;

sealed interface PatientActivityRequiringReview {
  long id();

  String type();

  long total();

  record CaseReport(
      long id,
      String type,
      long total
  ) implements PatientActivityRequiringReview {
  }


  record MorbidityReport(
      long id,
      long total
  ) implements PatientActivityRequiringReview {

    @Override
    public String type() {
      return "Morbidity Report";
    }
  }


  record LabReport(
      long id,
      long total
  ) implements PatientActivityRequiringReview {

    @Override
    public String type() {
      return "Laboratory Report";
    }
  }
}
