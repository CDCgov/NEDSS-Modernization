package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.support.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LabReportSearchCriteriaSteps {

  private final Active<PatientIdentifier> patient;
  private final Active<ProgramAreaIdentifier> programArea;
  private final Active<JurisdictionIdentifier> jurisdiction;
  private final Active<SearchableLabReport> searchableLabReport;
  private final Active<LabReportFilter> criteria;

  LabReportSearchCriteriaSteps(
      final Active<PatientIdentifier> patient,
      final Active<ProgramAreaIdentifier> programArea,
      final Active<JurisdictionIdentifier> jurisdiction,
      final Active<SearchableLabReport> searchableLabReport,
      final Active<LabReportFilter> criteria
  ) {
    this.patient = patient;
    this.programArea = programArea;
    this.jurisdiction = jurisdiction;
    this.searchableLabReport = searchableLabReport;
    this.criteria = criteria;
  }

  @Given("I add the lab report criteria for {string}")
  public void i_add_the_lab_report_criteria_for(final String field) {
    this.criteria.active(current -> applyCriteria(current, field));
  }

  private LabReportFilter applyCriteria(
      final LabReportFilter filter,
      final String field
  ) {
    if (field == null || field.isEmpty()) {
      return filter;
    }
    switch (field.toLowerCase()) {
      case "program area" -> programArea.maybeActive()
          .map(ProgramAreaIdentifier::code)
          .map(List::of)
          .ifPresent(filter::setProgramAreas);

      case "jurisdiction" -> jurisdiction.maybeActive()
          .map(found -> Long.valueOf(found.code()))
          .map(List::of)
          .ifPresent(filter::setJurisdictions);

      case "pregnancy status" -> this.searchableLabReport.maybeActive()
          .map(lab -> PregnancyStatus.resolve(lab.pregnancyStatus()))
          .ifPresent(filter::setPregnancyStatus);

      case "accession number" -> filler()
          .map(
              filler ->
                  eventId(
                      LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER,
                      filler
                  )
          ).ifPresent(filter::setEventId);

      case "lab id" -> this.searchableLabReport.maybeActive()
          .map(lab -> eventId(
                  LabReportFilter.LaboratoryEventIdType.LAB_ID,
                  lab.local()
              )
          );

      case "date of report" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_OF_REPORT,
                  lab.reportedOn()
              )
          ).ifPresent(filter::setEventDate);

      case "date received by public health" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_RECEIVED_BY_PUBLIC_HEALTH,
                  lab.receivedOn()
              )
          ).ifPresent(filter::setEventDate);

      case "date of specimen collection" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_OF_SPECIMEN_COLLECTION,
                  lab.collectedOn()
              )
          ).ifPresent(filter::setEventDate);

      case "lab report create date", "created" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.LAB_REPORT_CREATE_DATE,
                  lab.createdOn()
              )
          ).ifPresent(filter::setEventDate);

      case "lab report update date", "updated" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.LAST_UPDATE_DATE,
                  lab.updatedOn()
              )
          ).ifPresent(filter::setEventDate);

      case "entry method" -> {
        filter.setEntryMethods(List.of(LabReportFilter.EntryMethod.ELECTRONIC));
        filter.setEnteredBy(List.of(LabReportFilter.UserType.EXTERNAL));
      }
      case "entered by" -> filter.setEnteredBy(List.of(LabReportFilter.UserType.EXTERNAL));

      case "event status" -> filter.setEventStatus(List.of(LabReportFilter.EventStatus.NEW));

      case "processing status" -> processingStatus().map(List::of).ifPresent(filter::setProcessingStatus);

      case "created by" -> this.searchableLabReport.maybeActive().map(
          SearchableLabReport::createdBy
      ).ifPresent(filter::setCreatedBy);

      case "last updated by" -> this.searchableLabReport.maybeActive().map(
          SearchableLabReport::updatedBy
      ).ifPresent(filter::setLastUpdatedBy);

      case "ordering facility" -> orderingFacility().map(
          provider -> providerSearch(
              LabReportFilter.ProviderType.ORDERING_FACILITY,
              provider
          )
      );

      case "ordering provider" -> orderingProvider().map(
          provider -> providerSearch(
              LabReportFilter.ProviderType.ORDERING_PROVIDER,
              provider
          )
      );

      case "reporting facility" -> reportingFacility().map(
          provider -> providerSearch(
              LabReportFilter.ProviderType.REPORTING_FACILITY,
              provider
          )
      );
      case "resulted test" ->  test().map(SearchableLabReport.LabTest::name)
          .ifPresent(filter::setResultedTest);

      case "coded result" -> test().map(SearchableLabReport.LabTest::result)
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
      final String value
  ) {
    return new LabReportFilter.LabReportEventId(
        type,
        value
    );
  }

  private LabReportFilter.LaboratoryEventDateSearch eventDateSearch(
      final LabReportFilter.LabReportDateType type,
      final LocalDate from
  ) {
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

  private Optional<Long> orderingProvider() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(SearchableLabReport::people)
        .flatMap(Collection::stream)
        .filter(SearchableLabReport.Person.Provider.class::isInstance)
        .map(SearchableLabReport.Person.Provider.class::cast)
        .filter(participation -> Objects.equals("ORD", participation.type()))
        .findFirst()
        .map(SearchableLabReport.Person.Provider::identifier);
  }

  private Optional<String> filler() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(SearchableLabReport::identifiers)
        .flatMap(Collection::stream)
        .filter(act -> Objects.equals("FN", act.type()))
        .findFirst()
        .map(SearchableLabReport.Identifier::value);
  }

  private Optional<SearchableLabReport.LabTest> test() {
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
      final long value
  ) {
    return new LabReportFilter.LabReportProviderSearch(type, value);
  }
}
