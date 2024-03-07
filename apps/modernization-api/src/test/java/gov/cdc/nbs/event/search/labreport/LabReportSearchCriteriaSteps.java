package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchOrganizationParticipation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.support.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LabReportSearchCriteriaSteps {

  private final MotherSettings settings;
  private final Active<PatientIdentifier> patient;
  private final Active<ProgramAreaIdentifier> programArea;
  private final Active<JurisdictionIdentifier> jurisdiction;
  private final Active<LabReport> searchableLabReport;
  private final Active<LabReportFilter> criteria;

  LabReportSearchCriteriaSteps(
      final MotherSettings settings,
      final Active<PatientIdentifier> patient,
      final Active<ProgramAreaIdentifier> programArea,
      final Active<JurisdictionIdentifier> jurisdiction,
      final Active<LabReport> searchableLabReport,
      final Active<LabReportFilter> criteria
  ) {
    this.settings = settings;
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
          .map(lab -> PregnancyStatus.resolve(lab.getPregnantIndCd()))
          .ifPresent(filter::setPregnancyStatus);

      case "accession number" -> filter.setEventId(
          eventId(
              LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER,
              "accession number"
          )
      );

      case "lab id" -> this.searchableLabReport.maybeActive()
          .map(lab -> eventId(
                  LabReportFilter.LaboratoryEventIdType.LAB_ID,
                  lab.getLocalId()
              )
          );

      case "date of report" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_OF_REPORT,
                  lab.getActivityToTime()
              )
          ).ifPresent(filter::setEventDate);

      case "date received by public health" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_RECEIVED_BY_PUBLIC_HEALTH,
                  lab.getRptToStateTime()
              )
          ).ifPresent(filter::setEventDate);

      case "date of specimen collection" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.DATE_OF_SPECIMEN_COLLECTION,
                  lab.getEffectiveFromTime()
              )
          ).ifPresent(filter::setEventDate);

      case "lab report create date", "created" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.LAB_REPORT_CREATE_DATE,
                  lab.getAddTime()
              )
          ).ifPresent(filter::setEventDate);

      case "lab report update date", "updated" -> this.searchableLabReport.maybeActive()
          .map(
              lab -> eventDateSearch(
                  LabReportFilter.LabReportDateType.LAST_UPDATE_DATE,
                  lab.getObservationLastChgTime()
              )
          ).ifPresent(filter::setEventDate);

      case "entry method" -> {
        filter.setEntryMethods(List.of(LabReportFilter.EntryMethod.ELECTRONIC));
        filter.setEnteredBy(List.of(LabReportFilter.UserType.EXTERNAL));
      }
      case "entered by" -> filter.setEnteredBy(List.of(LabReportFilter.UserType.EXTERNAL));

      case "event status" -> filter.setEventStatus(List.of(LabReportFilter.EventStatus.NEW));

      case "processing status" -> filter.setProcessingStatus(List.of(LabReportFilter.ProcessingStatus.UNPROCESSED));

      case "created by" -> filter.setCreatedBy(settings.createdBy());

      case "last updated by" -> filter.setLastUpdatedBy(settings.createdBy());

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

      case "reporting facility" -> orderingFacility().map(
          provider -> providerSearch(
              LabReportFilter.ProviderType.REPORTING_FACILITY,
              provider
          )
      );
      case "resulted test" -> filter.setResultedTest("Acid-Fast Stain");

      case "coded result" -> filter.setCodedResult("abnormal");

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
      final Instant from
  ) {
    LocalDate localDate = from.atZone(ZoneId.systemDefault()).toLocalDate();
    return new LabReportFilter.LaboratoryEventDateSearch(
        type,
        localDate.minusDays(5),
        localDate.plusDays(2));
  }


  private Optional<Long> orderingFacility() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(LabReport::getOrganizationParticipations)
        .flatMap(Collection::stream)
        .filter(participation -> Objects.equals("ORD", participation.getTypeCd()))
        .findFirst()
        .map(ElasticsearchOrganizationParticipation::getEntityId);
  }

  private Optional<Long> reportingFacility() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(LabReport::getOrganizationParticipations)
        .flatMap(Collection::stream)
        .filter(participation -> Objects.equals("AUT", participation.getTypeCd()))
        .findFirst()
        .map(ElasticsearchOrganizationParticipation::getEntityId);
  }

  private Optional<Long> orderingProvider() {
    return this.searchableLabReport.maybeActive()
        .stream()
        .map(LabReport::getPersonParticipations)
        .flatMap(Collection::stream)
        .filter(participation -> Objects.equals("PATSBJ", participation.getTypeCd()))
        .findFirst()
        .map(ElasticsearchPersonParticipation::getEntityId);
  }

  private LabReportFilter.LabReportProviderSearch providerSearch(
      final LabReportFilter.ProviderType type,
      final long value
  ) {
    return new LabReportFilter.LabReportProviderSearch(type, value);
  }
}
