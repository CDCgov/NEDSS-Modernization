package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.event.document.CaseReportIdentifier;
import gov.cdc.nbs.event.investigation.AbcCaseIdentifier;
import gov.cdc.nbs.event.investigation.CityCountyCaseIdentifier;
import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.investigation.NotificationIdentifier;
import gov.cdc.nbs.event.investigation.StateCaseIdentifier;
import gov.cdc.nbs.event.report.lab.AccessionIdentifier;
import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.patient.profile.vaccination.VaccinationIdentifier;
import gov.cdc.nbs.patient.treatment.TreatmentIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class PatientSearchEventIdSteps {
  private final Active<PatientFilter> activeCriteria;
  private final Active<MorbidityReportIdentifier> activeMorbidityReport;
  private final Active<LabReportIdentifier> activeLabReport;
  private final Active<CaseReportIdentifier> activeCaseReport;
  private final Active<StateCaseIdentifier> activeStateCase;
  private final Active<AbcCaseIdentifier> activeAbcCase;
  private final Active<CityCountyCaseIdentifier> activeCityCountyCase;
  private final Active<NotificationIdentifier> activeNotification;
  private final Active<VaccinationIdentifier> activeVaccination;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<TreatmentIdentifier> activeTreatment;
  private final Active<AccessionIdentifier> activeAccessionNumber;

  PatientSearchEventIdSteps(
      final Active<PatientFilter> activeCriteria,
      final Active<LabReportIdentifier> activeLabReport,
      final Active<CaseReportIdentifier> activeCaseReport,
      final Active<StateCaseIdentifier> activeStateCase,
      final Active<AbcCaseIdentifier> activeAbcCase,
      final Active<CityCountyCaseIdentifier> activeCityCountyCase,
      final Active<NotificationIdentifier> activeNotification,
      final Active<TreatmentIdentifier> activeTreatment,
      final Active<VaccinationIdentifier> activeVaccination,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<MorbidityReportIdentifier> activeMorbidityReport,
      final Active<AccessionIdentifier> activeAccessionNumber) {
    this.activeCriteria = activeCriteria;
    this.activeMorbidityReport = activeMorbidityReport;
    this.activeLabReport = activeLabReport;
    this.activeStateCase = activeStateCase;
    this.activeAbcCase = activeAbcCase;
    this.activeCityCountyCase = activeCityCountyCase;
    this.activeNotification = activeNotification;
    this.activeTreatment = activeTreatment;
    this.activeVaccination = activeVaccination;
    this.activeInvestigation = activeInvestigation;
    this.activeCaseReport = activeCaseReport;
    this.activeAccessionNumber = activeAccessionNumber;
  }

  @Given("I would like to search for a patient using the Morbidity Report ID")
  public void i_would_like_to_search_for_a_patient_using_the_morbidity_report_ID() {
    this.activeMorbidityReport.maybeActive()
        .map(MorbidityReportIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withMorbidity(identifier)));
  }

  @Given("I would like to search for a patient using the Document ID")
  public void i_would_like_to_search_for_a_patient_using_the_document_ID() {
    this.activeCaseReport.maybeActive()
        .map(CaseReportIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withDocument(identifier)));
  }

  @Given("I would like to search for a patient using the State Case ID")
  public void i_would_like_to_search_for_a_patient_using_the_state_case_ID() {
    this.activeStateCase.maybeActive()
        .map(StateCaseIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withStateCase(identifier)));
  }

  @Given("I would like to search for a patient using the ABC Case ID")
  public void i_would_like_to_search_for_a_patient_using_the_abc_case_ID() {
    this.activeAbcCase.maybeActive()
        .map(AbcCaseIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withAbcCase(identifier)));
  }

  @Given("I would like to search for a patient using the County Case ID")
  public void i_would_like_to_search_for_a_patient_using_the_county_case_ID() {
    this.activeCityCountyCase.maybeActive()
        .map(CityCountyCaseIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withCityCountyCase(identifier)));
  }

  @Given("I would like to search for a patient using the Notification ID")
  public void i_would_like_to_search_for_a_patient_using_the_notification_ID() {
    this.activeNotification.maybeActive()
        .map(NotificationIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withNotification(identifier)));
  }

  @Given("I would like to search for a patient using the Treatment ID")
  public void i_would_like_to_search_for_a_patient_using_the_treatment_ID() {
    this.activeTreatment.maybeActive()
        .map(TreatmentIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withTreatment(identifier)));
  }

  @Given("I would like to search for a patient using the Vaccination ID")
  public void i_would_like_to_search_for_a_patient_using_the_vaccination_ID() {
    this.activeVaccination.maybeActive()
        .map(VaccinationIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withVaccination(identifier)));
  }

  @Given("I would like to search for a patient using the Investigation ID")
  public void i_would_like_to_search_for_a_patient_using_the_investigation_ID() {
    this.activeInvestigation.maybeActive()
        .map(InvestigationIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withInvestigation(identifier)));
  }

  @Given("I would like to search for a patient using the Lab Report ID")
  public void i_would_like_to_search_for_a_patient_using_the_lab_report_ID() {
    this.activeLabReport.maybeActive()
        .map(LabReportIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withLabReport(identifier)));
  }

  @Given("I would like to search for a patient using the Accession number")
  public void i_would_like_to_search_for_a_patient_using_the_accession_number() {
    this.activeAccessionNumber.maybeActive()
        .map(AccessionIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withAccessionNumber(identifier)));
  }
}
