package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.investigation.NotificationIdentifier;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class InvestigationSearchCriteriaSteps {

  private final Active<InvestigationFilter> activeCriteria;
  private final Active<PatientIdentifier> activePatient;
  private final Active<NotificationIdentifier> activeNotification;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<ProviderIdentifier> activeProvider;

  InvestigationSearchCriteriaSteps(
      final Active<InvestigationFilter> activeCriteria,
      final Active<PatientIdentifier> activePatient,
      final Active<NotificationIdentifier> activeNotification,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<ProviderIdentifier> activeProvider) {
    this.activeCriteria = activeCriteria;
    this.activePatient = activePatient;
    this.activeNotification = activeNotification;
    this.activeInvestigation = activeInvestigation;
    this.activeProvider = activeProvider;
  }

  @Given("I want to find investigations created by {user}")
  public void i_want_to_find_investigations_created_by(final ActiveUser user) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setCreatedBy(String.valueOf(user.id())));
  }

  @Given("I want to find investigations created on {localDate}")
  public void i_want_to_find_investigations_created_on(final LocalDate on) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventDate(
            eventDateSearch(
                InvestigationFilter.EventDateType.INVESTIGATION_CREATE_DATE,
                on)));
  }

  @Given("I want to find investigations updated by {user}")
  public void i_want_to_find_investigations_updated_by(final ActiveUser user) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setLastUpdatedBy(String.valueOf(user.id())));
  }

  @Given("I want to find investigations updated on {localDate}")
  public void i_want_to_find_investigations_updated_on(final LocalDate on) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventDate(
            eventDateSearch(
                InvestigationFilter.EventDateType.LAST_UPDATE_DATE,
                on)));
  }

  @Given("I want to find investigations started on {localDate}")
  public void i_want_to_find_investigations_started_on(final LocalDate on) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventDate(
            eventDateSearch(
                InvestigationFilter.EventDateType.INVESTIGATION_START_DATE,
                on)));
  }

  @Given("I want to find investigations reported on {localDate}")
  public void i_want_to_find_investigations_reported_on(final LocalDate on) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventDate(
            eventDateSearch(
                InvestigationFilter.EventDateType.DATE_OF_REPORT,
                on)));
  }

  @Given("I want to find investigations notified on {localDate}")
  public void i_want_to_find_investigations_notified_on(final LocalDate on) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventDate(
            eventDateSearch(
                InvestigationFilter.EventDateType.NOTIFICATION_CREATE_DATE,
                on)));
  }

  @Given("I want to find open investigations")
  public void i_want_to_find_open_investigations() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setInvestigationStatus(InvestigationFilter.InvestigationStatus.OPEN));
  }

  @Given("I want to find closed investigations")
  public void i_want_to_find_closed_investigations() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setInvestigationStatus(InvestigationFilter.InvestigationStatus.CLOSED));
  }

  @Given("I want to find investigations closed on {localDate}")
  public void i_want_to_find_investigations_closed_on(final LocalDate on) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventDate(
            eventDateSearch(
                InvestigationFilter.EventDateType.INVESTIGATION_CLOSED_DATE,
                on)));
  }

  private InvestigationFilter.EventDate eventDateSearch(
      final InvestigationFilter.EventDateType type,
      final LocalDate from) {
    return new InvestigationFilter.EventDate(
        type,
        from.minusDays(5),
        from.plusDays(2));
  }

  @Given("I want to find investigations with a processing status of {processingCriteria}")
  public void i_want_to_find_investigations_with_a_processing_status_of(
      final InvestigationFilter.ProcessingStatus status) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.withProcessingStatus(status));
  }

  @ParameterType(name = "processingCriteria", value = ".*")
  public InvestigationFilter.ProcessingStatus processingCriteria(final String value) {
    return switch (value.toLowerCase()) {
      case "awaiting interview" -> InvestigationFilter.ProcessingStatus.AWAITING_INTERVIEW;
      case "closed case" -> InvestigationFilter.ProcessingStatus.CLOSED_CASE;
      case "field follow-up" -> InvestigationFilter.ProcessingStatus.FIELD_FOLLOW_UP;
      case "no follow-up" -> InvestigationFilter.ProcessingStatus.NO_FOLLOW_UP;
      case "open case" -> InvestigationFilter.ProcessingStatus.OPEN_CASE;
      case "surveillance follow-up" -> InvestigationFilter.ProcessingStatus.SURVEILLANCE_FOLLOW_UP;
      default -> InvestigationFilter.ProcessingStatus.UNASSIGNED;
    };
  }

  @Given("I want to find investigations with a case status of {caseCriteria}")
  public void i_want_to_find_investigations_with_a_case_status_of(final InvestigationFilter.CaseStatus status) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.withCaseStatus(status));
  }

  @ParameterType(name = "caseCriteria", value = ".*")
  public InvestigationFilter.CaseStatus caseCriteria(final String value) {
    return switch (value.toLowerCase()) {
      case "confirmed" -> InvestigationFilter.CaseStatus.CONFIRMED;
      case "not a case" -> InvestigationFilter.CaseStatus.NOT_A_CASE;
      case "probable" -> InvestigationFilter.CaseStatus.PROBABLE;
      case "suspect" -> InvestigationFilter.CaseStatus.SUSPECT;
      case "unknown" -> InvestigationFilter.CaseStatus.UNKNOWN;
      default -> InvestigationFilter.CaseStatus.UNASSIGNED;
    };
  }

  @Given("I want to find investigations with a notification status of {notificationCriteria}")
  public void i_want_to_find_investigations_with_a_notification_status_of(
      final InvestigationFilter.NotificationStatus status) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.withNotificationStatus(status));
  }

  @ParameterType(name = "notificationCriteria", value = ".*")
  public InvestigationFilter.NotificationStatus notificationCriteria(final String value) {
    return switch (value.toLowerCase()) {
      case "approved" -> InvestigationFilter.NotificationStatus.APPROVED;
      case "completed" -> InvestigationFilter.NotificationStatus.COMPLETED;
      case "message failed" -> InvestigationFilter.NotificationStatus.MESSAGE_FAILED;
      case "pending approval" -> InvestigationFilter.NotificationStatus.PENDING_APPROVAL;
      case "rejected" -> InvestigationFilter.NotificationStatus.REJECTED;
      default -> InvestigationFilter.NotificationStatus.UNASSIGNED;
    };
  }

  @Given("I want to find investigations within the {programArea} Program Area")
  public void i_want_to_find_investigations_within_the_program_area(final ProgramAreaIdentifier programArea) {
    this.activeCriteria.maybeActive()
        .ifPresent(
            criteria -> criteria.withProgramArea(programArea.code()));
  }

  @Given("I want to find investigations within the {jurisdiction} Jurisdiction")
  public void i_want_to_find_investigations_within_the_jurisdiction(final JurisdictionIdentifier jurisdiction) {
    this.activeCriteria.maybeActive()
        .ifPresent(
            criteria -> criteria.withJurisdiction(Long.parseLong(jurisdiction.code())));
  }

  @Given("I want to find investigations for the patient")
  public void i_want_to_find_investigations_for_the_patient() {
    this.activeCriteria.maybeActive()
        .ifPresent(
            criteria -> criteria.setPatientId(this.activePatient.active().id()));
  }

  @Given("I want to find investigations for patients that are pregnant")
  public void i_want_to_find_investigations_for_pregnant_patients() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setPregnancyStatus(PregnancyStatus.YES));
  }

  @Given("I want to find investigations for patients that are not pregnant")
  public void i_want_to_find_investigations_for_patients_that_are_not_pregnant() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setPregnancyStatus(PregnancyStatus.NO));
  }

  @Given("I want to find investigations for patients that do not know if they are pregnant")
  public void i_want_to_find_investigations_for_patients_that_do_not_know_if_they_are_pregnant() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setPregnancyStatus(PregnancyStatus.UNKNOWN));
  }

  @Given("I want to find investigations for the {condition} condition")
  public void i_want_to_find_investigations_for_the_condition(final String condition) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.withCondition(condition));
  }

  @Given("I want to find the investigation by id")
  public void i_want_to_find_investigations_by_id() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventId(
            new InvestigationFilter.InvestigationEventId(
                InvestigationFilter.IdType.INVESTIGATION_ID,
                this.activeInvestigation.active().local())));
  }

  @Given("I want to find investigations for the notification")
  public void i_want_to_find_investigations_for_the_notification() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventId(
            new InvestigationFilter.InvestigationEventId(
                InvestigationFilter.IdType.NOTIFICATION_ID,
                this.activeNotification.active().local())));
  }

  @Given("I want to find investigations for the State Case {string}")
  public void i_want_to_find_investigations_for_state_case(final String number) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventId(
            new InvestigationFilter.InvestigationEventId(
                InvestigationFilter.IdType.STATE_CASE_ID,
                number)));
  }

  @Given("I want to find investigations for the County Case {string}")
  public void i_want_to_find_investigations_for_county_case(final String number) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventId(
            new InvestigationFilter.InvestigationEventId(
                InvestigationFilter.IdType.CITY_COUNTY_CASE_ID,
                number)));
  }

  @Given("I want to find investigations for the ABCs Case {string}")
  public void i_want_to_find_investigations_for_ABCS_case(final String number) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventId(
            new InvestigationFilter.InvestigationEventId(
                InvestigationFilter.IdType.ABCS_CASE_ID,
                number)));
  }

  @Given("I want to find investigations investigated by the provider")
  public void i_want_to_find_investigations_investigated_by_the_provider() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setInvestigatorId(this.activeProvider.active().identifier()));
  }

  @Given("I want to find investigations reported by the {organization} facility")
  public void i_want_to_find_investigations_reported_by_the_organization(final OrganizationIdentifier organization) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setProviderFacilitySearch(
            new InvestigationFilter.ProviderFacilitySearch(
                InvestigationFilter.ReportingEntityType.FACILITY,
                organization.identifier())));
  }

  @Given("I want to find investigations reported by the {organization} facility using the new api")
  public void i_want_to_find_investigations_reported_by_the_organization_using_the_new_api(final OrganizationIdentifier organization) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setReportingFacilityId(String.valueOf(organization.identifier())));
  }

  @Given("I want to find investigations reported by the provider")
  public void i_want_to_find_investigations_reported_by_the_provider() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setProviderFacilitySearch(
            new InvestigationFilter.ProviderFacilitySearch(
                InvestigationFilter.ReportingEntityType.PROVIDER,
                this.activeProvider.active().identifier())));
  }

  @Given("I want to find investigations reported by the provider using the new api")
  public void i_want_to_find_investigations_reported_by_the_provider_using_the_new_api() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setReportingProviderId(String.valueOf(this.activeProvider.active().identifier())));
  }

  @Given("I want to find investigations related to the {outbreak} outbreak")
  public void i_want_to_find_investigations_related_to_the_outbreak(final String outbreak) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.withOutbreak(outbreak));
  }
}
