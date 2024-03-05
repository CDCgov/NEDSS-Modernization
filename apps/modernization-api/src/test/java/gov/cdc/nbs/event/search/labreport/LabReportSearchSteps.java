package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.event.search.LabReportFilter.EntryMethod;
import gov.cdc.nbs.event.search.LabReportFilter.EventStatus;
import gov.cdc.nbs.event.search.LabReportFilter.LabReportDateType;
import gov.cdc.nbs.event.search.LabReportFilter.LabReportEventId;
import gov.cdc.nbs.event.search.LabReportFilter.LabReportProviderSearch;
import gov.cdc.nbs.event.search.LabReportFilter.LaboratoryEventDateSearch;
import gov.cdc.nbs.event.search.LabReportFilter.LaboratoryEventIdType;
import gov.cdc.nbs.event.search.LabReportFilter.ProcessingStatus;
import gov.cdc.nbs.event.search.LabReportFilter.ProviderType;
import gov.cdc.nbs.event.search.LabReportFilter.UserType;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;
import gov.cdc.nbs.repository.elasticsearch.LabReportRepository;
import gov.cdc.nbs.search.support.SortCriteria;
import gov.cdc.nbs.support.EventMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class LabReportSearchSteps {
  private static final Long PATIENT_ID = 8888888L;

  private final LabReportSearchRequester requester;
  private final Active<Pageable> paging;
  private final Active<SortCriteria> sorting;
  private final Active<ResultActions> response;

  private final LabReportRepository labReportRepository;
  private final JurisdictionCodeRepository jurisdictionCodeRepository;

  LabReportSearchSteps(
      final LabReportSearchRequester requester,
      final Active<Pageable> paging,
      final Active<SortCriteria> sorting,
      final Active<ResultActions> response,
      final LabReportRepository labReportRepository,
      final JurisdictionCodeRepository jurisdictionCodeRepository
  ) {
    this.requester = requester;
    this.paging = paging;
    this.sorting = sorting;
    this.response = response;
    this.labReportRepository = labReportRepository;
    this.jurisdictionCodeRepository = jurisdictionCodeRepository;
  }

  @Given("A lab report exist")
  public void lab_report_exist() {
    addJurisdictionEntries();
    var labReport = EventMother.labReport_acidFastStain(PATIENT_ID);
    labReportRepository.save(labReport);
  }

  @When("I search laboratory events by {string} {string}")
  public void i_search_patients_by_laboratory_events(String field, String qualifier) {
    var filter = updateLabReportFilter(new LabReportFilter(), field, qualifier);
    search(filter);
  }

  private void search(final LabReportFilter filter) {
    this.response.active(
        requester.search(
            filter,
            paging.active(),
            sorting.active()
        )
    );
  }

  @When("I search laboratory events by {string} {string} {string} {string} {string} {string}")
  public void i_search_patients_by_laboratory_events(String field, String qualifier, String field2, String qualifier2,
      String field3, String qualifier3) {
    LabReportFilter filter = updateLabReportFilter(new LabReportFilter(), field, qualifier);
    updateLabReportFilter(filter, field2, qualifier2);
    updateLabReportFilter(filter, field3, qualifier3);
    search(filter);
  }

  @Then("I find the lab report")
  public void i_find_the_lab_report() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findLabReportsByFilter.total").value(greaterThan(0)));

  }

  private void addJurisdictionEntries() {
    var jurisdictions = EventMother.getJurisdictionCodes();
    jurisdictionCodeRepository.saveAll(jurisdictions);
  }

  private LabReportFilter updateLabReportFilter(LabReportFilter filter, String field,
      String qualifier) {
    if (field == null || field.isEmpty()) {
      return filter;
    }
    switch (field) {
      case "program area":
        filter.setProgramAreas(List.of("STD"));
        break;
      case "jurisdiction":
        filter.setJurisdictions(Collections.singletonList(EventMother.CLAYTON_CODE));
        break;
      case "pregnancy status":
        filter.setPregnancyStatus(PregnancyStatus.YES);
        break;
      case "event id":
        switch (qualifier) {
          case "accession number":
            filter.setEventId(
                new LabReportEventId(LaboratoryEventIdType.ACCESSION_NUMBER, "accession number"));
            break;
          case "lab id":
            filter.setEventId(new LabReportEventId(LaboratoryEventIdType.LAB_ID, "OBS10003024GA01"));
            break;
        }
        break;
      case "event date":
        var eds = new LaboratoryEventDateSearch(
            LabReportDateType.valueOf(qualifier),
            LocalDate.now().minusDays(5),
            LocalDate.now().plusDays(2));
        filter.setEventDate(eds);
        break;
      case "entry method":
        filter.setEntryMethods(List.of(EntryMethod.ELECTRONIC));
        filter.setEnteredBy(List.of(UserType.EXTERNAL));
        break;
      case "entered by":
        filter.setEnteredBy(List.of(UserType.EXTERNAL));
        break;
      case "event status":
        filter.setEventStatus(List.of(EventStatus.NEW));
        break;
      case "processing status":
        filter.setProcessingStatus(List.of(ProcessingStatus.UNPROCESSED));
        break;
      case "created by":
        filter.setCreatedBy(EventMother.CREATED_BY);
        break;
      case "last updated by":
        filter.setLastUpdatedBy(EventMother.UPDATED_BY);
        break;
      case "provider search":
        var ps = new LabReportProviderSearch(ProviderType.valueOf(qualifier), PATIENT_ID);
        filter.setProviderSearch(ps);
        break;
      case "resulted test":
        filter.setResultedTest("Acid-Fast Stain");
        break;
      case "coded result":
        filter.setCodedResult("abnormal");
        break;
      case "patient id":
        filter.setPatientId(PATIENT_ID);
        break;
      default:
        throw new IllegalArgumentException("Unsupported field: " + field);
    }

    return filter;
  }
}
