package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LabReportSearchCriteriaSteps {

  private final Active<PatientIdentifier> patient;
  private final Active<ProviderIdentifier> activeProvider;
  private final Active<SearchableLabReport> searchableLabReport;
  private final Active<LabReportFilter> activeCriteria;

  LabReportSearchCriteriaSteps(
      final Active<PatientIdentifier> patient,
      final Active<ProviderIdentifier> activeProvider,
      final Active<SearchableLabReport> searchableLabReport,
      final Active<LabReportFilter> activeCriteria) {
    this.patient = patient;
    this.activeProvider = activeProvider;
    this.searchableLabReport = searchableLabReport;
    this.activeCriteria = activeCriteria;
  }

  @Given("I want to find lab reports created by {user}")
  public void i_want_to_find_lab_reports_created_by(final ActiveUser user) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setCreatedBy(user.id()));
  }

  @Given("I want to find lab reports created on {localDate}")
  public void i_want_to_find_lab_reports_created_on(final LocalDate on) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventDate(
            eventDateSearch(
                LabReportFilter.LabReportDateType.LAB_REPORT_CREATE_DATE,
                on)));
  }

  @Given("I want to find lab reports updated by {user}")
  public void i_want_to_find_lab_reports_updated_by(final ActiveUser user) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setLastUpdatedBy(user.id()));
  }

  @Given("I want to find lab reports updated on {localDate}")
  public void i_want_to_find_lab_reports_updated_on(final LocalDate on) {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> criteria.setEventDate(
            eventDateSearch(
                LabReportFilter.LabReportDateType.LAST_UPDATE_DATE,
                on)));
  }

  @Given("I want to find new lab reports")
  public void i_want_to_find_new_lab_reports() {
    this.activeCriteria.active(criteria -> criteria.withEventStatus(LabReportFilter.EventStatus.NEW));
  }

  @Given("I want to find updated lab reports")
  public void i_want_to_find_updated_lab_reports() {
    this.activeCriteria.active(criteria -> criteria.withEventStatus(LabReportFilter.EventStatus.UPDATE));
  }

  @Given("I want to find lab reports ordered by the provider")
  public void i_want_to_find_lab_reports_ordered_by_the_provider() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> this.activeProvider.maybeActive().ifPresent(
            provider -> criteria.setProviderSearch(
                providerSearch(
                    LabReportFilter.ProviderType.ORDERING_PROVIDER,
                    provider.identifier()))));
  }

  @Given("I want to find lab reports ordered by the provider using the new api")
  public void i_want_to_find_lab_reports_ordered_by_the_provider_and_new_api() {
    this.activeCriteria.maybeActive().ifPresent(
        criteria -> this.activeProvider.maybeActive().ifPresent(
            provider -> criteria.setOrderingProviderId(provider.identifier())));
  }

  @Given("I want to find lab reports with test results for {string}")
  public void i_want_to_find_lab_reports_with_test_results_for(final String value) {
    this.activeCriteria.active(criteria -> criteria.withResultedTest(value));
  }

  @Given("I want to find lab reports with the coded result {string}")
  public void i_want_to_find_lab_reports_with_the_coded_result(final String value) {
    this.activeCriteria.active(criteria -> criteria.withCodedResult(value));
  }

  @Given("I add the lab report criteria for {string}")
  public void i_add_the_lab_report_criteria_for(final String field) {
    this.activeCriteria.active(current -> applyCriteria(current, field));
  }

  private LabReportFilter applyCriteria(
      final LabReportFilter filter,
      final String field) {
    if (field == null || field.isEmpty()) {
      return filter;
    }
    switch (field.toLowerCase()) {
      case "program area" -> this.searchableLabReport.maybeActive()
          .map(SearchableLabReport::programArea)
          .map(List::of)
          .ifPresent(filter::setProgramAreas);

      case "jurisdiction" -> this.searchableLabReport.maybeActive()
          .map(found -> Long.valueOf(found.jurisdiction()))
          .map(List::of)
          .ifPresent(filter::setJurisdictions);

      case "pregnancy status" -> this.searchableLabReport.maybeActive()
          .map(lab -> PregnancyStatus.resolve(lab.pregnancyStatus()))
          .ifPresent(filter::setPregnancyStatus);

      case "accession number" -> accession()
          .map(
              filler -> eventId(
                  LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER,
                  filler))
          .ifPresent(filter::setEventId);

      case "lab id" -> this.searchableLabReport.maybeActive()
          .map(lab -> eventId(
              LabReportFilter.LaboratoryEventIdType.LAB_ID,
              lab.local()))
          .ifPresent(filter::setEventId);

      case "date of report" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_OF_REPORT,
                  lab.reportedOn()))
          .ifPresent(filter::setEventDate);

      case "date received by public health" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_RECEIVED_BY_PUBLIC_HEALTH,
                  lab.receivedOn()))
          .ifPresent(filter::setEventDate);

      case "date of specimen collection" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_OF_SPECIMEN_COLLECTION,
                  lab.collectedOn()))
          .ifPresent(filter::setEventDate);

      case "entry method" -> {
        filter.setEntryMethods(List.of(LabReportFilter.EntryMethod.ELECTRONIC));
        filter.setEnteredBy(List.of(LabReportFilter.UserType.EXTERNAL));
      }
      case "entered by" -> filter.setEnteredBy(List.of(LabReportFilter.UserType.EXTERNAL));

      case "processing status" -> processingStatus().map(List::of).ifPresent(filter::setProcessingStatus);

      case "ordering facility" -> orderingFacility().map(
              provider -> providerSearch(
                  LabReportFilter.ProviderType.ORDERING_FACILITY,
                  provider))
          .ifPresent(filter::setProviderSearch);

      case "ordering facility new api" -> orderingFacility()
          .ifPresent(filter::setOrderingLabId);

      case "reporting facility" -> reportingFacility().map(
              provider -> providerSearch(
                  LabReportFilter.ProviderType.REPORTING_FACILITY,
                  provider))
          .ifPresent(filter::setProviderSearch);

      case "reporting facility new api" -> reportingFacility()
          .ifPresent(filter::setReportingLabId);

      case "resulted test" -> tests().map(SearchableLabReport.LabTest::name)
          .ifPresent(filter::setResultedTest);

      case "coded result" -> tests().map(SearchableLabReport.LabTest::result)
          .ifPresent(filter::setCodedResult);

      case "patient id" -> this.patient.maybeActive()
          .map(PatientIdentifier::id)
          .ifPresent(filter::setPatientId);

      default -> throw new IllegalArgumentException("Unsupported field: " + field);
    }

    return filter;
  }

  private LabReportFilter.LabReportEventId eventId(
      final LabReportFilter.LaboratoryEventIdType type,
      final String value) {
    return new LabReportFilter.LabReportEventId(
        type,
        value);
  }

  private LabReportFilter.LaboratoryEventDateSearch eventDateSearch(
      final LabReportFilter.LabReportDateType type,
      final LocalDate from) {
    return new LabReportFilter.LaboratoryEventDateSearch(
        type,
        from.minusDays(5),
        from.plusDays(2));
  }


  private Optional<Long> orderingFacility() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(SearchableLabReport::organizations)
        .flatMap(Collection::stream)
        .filter(participation -> Objects.equals("ORD", participation.type()))
        .findFirst()
        .map(SearchableLabReport.Organization::identifier);
  }

  private Optional<Long> reportingFacility() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(SearchableLabReport::organizations)
        .flatMap(Collection::stream)
        .filter(participation -> Objects.equals("AUT", participation.type()))
        .findFirst()
        .map(SearchableLabReport.Organization::identifier);
  }

  private Optional<String> accession() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(SearchableLabReport::identifiers)
        .flatMap(Collection::stream)
        .filter(act -> Objects.equals("FN", act.type()))
        .findFirst()
        .map(SearchableLabReport.Identifier::value);
  }

  private Optional<SearchableLabReport.LabTest> tests() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(SearchableLabReport::tests)
        .flatMap(Collection::stream)
        .findFirst();
  }

  private Optional<LabReportFilter.ProcessingStatus> processingStatus() {
    return this.searchableLabReport.maybeActive()
        .map(SearchableLabReport::status)
        .map(this::resolveProcessingStatus);
  }

  private LabReportFilter.ProcessingStatus resolveProcessingStatus(final String status) {
    return Objects.equals(status, "UNPROCESSED")
        ? LabReportFilter.ProcessingStatus.UNPROCESSED
        : LabReportFilter.ProcessingStatus.PROCESSED;
  }

  private LabReportFilter.LabReportProviderSearch providerSearch(
      final LabReportFilter.ProviderType type,
      final long value) {
    return new LabReportFilter.LabReportProviderSearch(type, value);
  }
}
