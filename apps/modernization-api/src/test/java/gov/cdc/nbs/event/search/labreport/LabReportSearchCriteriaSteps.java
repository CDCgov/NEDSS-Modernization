package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.EventMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.util.List;

public class LabReportSearchCriteriaSteps {

  private static final Long PATIENT_ID = 8888888L;

  private final Active<PatientIdentifier> patient;
  private final Active<LabReportFilter> criteria;

  LabReportSearchCriteriaSteps(
      final Active<PatientIdentifier> patient,
      final Active<LabReportFilter> criteria
  ) {
    this.patient = patient;
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
      case "program area" -> filter.setProgramAreas(List.of("STD"));

      case "jurisdiction" -> filter.setJurisdictions(List.of(EventMother.CLAYTON_CODE));

      case "pregnancy status" -> filter.setPregnancyStatus(PregnancyStatus.YES);

      case "accession number" -> filter.setEventId(
          new LabReportFilter.LabReportEventId(
              LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER,
              "accession number")
      );

      case "lab id" -> filter.setEventId(
          new LabReportFilter.LabReportEventId(
              LabReportFilter.LaboratoryEventIdType.LAB_ID,
              "OBS10003024GA01"
          )
      );

      case "date of report" -> filter.setEventDate(
          eventDateSearch(
              LabReportFilter.LabReportDateType.DATE_OF_REPORT
          )
      );
      case "date received by public health" -> filter.setEventDate(
          eventDateSearch(
              LabReportFilter.LabReportDateType.DATE_RECEIVED_BY_PUBLIC_HEALTH
          )
      );
      case "date of specimen collection" -> filter.setEventDate(
          eventDateSearch(
              LabReportFilter.LabReportDateType.DATE_OF_SPECIMEN_COLLECTION
          )
      );
      case "lab report create date", "created" -> filter.setEventDate(
          eventDateSearch(
              LabReportFilter.LabReportDateType.LAB_REPORT_CREATE_DATE
          )
      );
      case "lab report update date", "updated" -> filter.setEventDate(
          eventDateSearch(
              LabReportFilter.LabReportDateType.LAST_UPDATE_DATE
          )
      );
      case "entry method" -> {
        filter.setEntryMethods(List.of(LabReportFilter.EntryMethod.ELECTRONIC));
        filter.setEnteredBy(List.of(LabReportFilter.UserType.EXTERNAL));
      }
      case "entered by" -> filter.setEnteredBy(List.of(LabReportFilter.UserType.EXTERNAL));

      case "event status" -> filter.setEventStatus(List.of(LabReportFilter.EventStatus.NEW));

      case "processing status" -> filter.setProcessingStatus(List.of(LabReportFilter.ProcessingStatus.UNPROCESSED));

      case "created by" -> filter.setCreatedBy(EventMother.CREATED_BY);

      case "last updated by" -> filter.setLastUpdatedBy(EventMother.UPDATED_BY);

      case "ordering facility" -> filter.setProviderSearch(
          providerSearch(
              LabReportFilter.ProviderType.ORDERING_FACILITY,
              PATIENT_ID
          )
      );
      case "ordering provider" -> filter.setProviderSearch(
          providerSearch(
              LabReportFilter.ProviderType.ORDERING_PROVIDER,
              PATIENT_ID
          )
      );
      case "reporting facility" -> filter.setProviderSearch(
          providerSearch(
              LabReportFilter.ProviderType.REPORTING_FACILITY,
              PATIENT_ID
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

  private LabReportFilter.LaboratoryEventDateSearch eventDateSearch(final LabReportFilter.LabReportDateType type) {
    return new LabReportFilter.LaboratoryEventDateSearch(
        type,
        LocalDate.now().minusDays(5),
        LocalDate.now().plusDays(2));
  }

  private LabReportFilter.LabReportProviderSearch providerSearch(
      final LabReportFilter.ProviderType type,
      final long value
  ) {
    return new LabReportFilter.LabReportProviderSearch(type, value);
  }
}
