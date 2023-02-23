package gov.cdc.nbs;

import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.controller.EventController;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.graphql.filter.LabReportFilter;
import gov.cdc.nbs.graphql.filter.LabReportFilter.EntryMethod;
import gov.cdc.nbs.graphql.filter.LabReportFilter.EventStatus;
import gov.cdc.nbs.graphql.filter.LabReportFilter.LabReportDateType;
import gov.cdc.nbs.graphql.filter.LabReportFilter.LabReportProviderSearch;
import gov.cdc.nbs.graphql.filter.LabReportFilter.LaboratoryEventDateSearch;
import gov.cdc.nbs.graphql.filter.LabReportFilter.LaboratoryEventIdType;
import gov.cdc.nbs.graphql.filter.LabReportFilter.ProcessingStatus;
import gov.cdc.nbs.graphql.filter.LabReportFilter.ProviderType;
import gov.cdc.nbs.graphql.filter.LabReportFilter.UserType;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;
import gov.cdc.nbs.repository.elasticsearch.LabReportRepository;
import gov.cdc.nbs.support.EventMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class LabReportSearchSteps {
    @Autowired
    private LabReportRepository labReportRepository;
    @Autowired
    private JurisdictionCodeRepository jurisdictionCodeRepository;
    @Autowired
    private EventController eventController;
    private List<LabReport> labReportSearchResults;

    private static final Long PATIENT_ID = 8888888L;

    @Given("A lab report exist")
    public void lab_report_exist() {
        addJurisdictionEntries();
        var labReport = EventMother.labReport_acidFastStain(PATIENT_ID);
        labReportRepository.save(labReport);
    }

    @When("I search laboratory events by {string} {string}")
    public void i_search_patients_by_laboratory_events(String field, String qualifier) {
        var filter = updateLabReportFilter(new LabReportFilter(), field, qualifier);
        labReportSearchResults = eventController.findLabReportsByFilter(filter, null).getContent();
    }

    @When("I search laboratory events by {string} {string} {string} {string} {string} {string}")
    public void i_search_patients_by_laboratory_events(String field, String qualifier, String field2, String qualifier2,
            String field3, String qualifier3) {
        LabReportFilter filter = updateLabReportFilter(new LabReportFilter(), field, qualifier);
        updateLabReportFilter(filter, field2, qualifier2);
        updateLabReportFilter(filter, field3, qualifier3);
        labReportSearchResults = eventController.findLabReportsByFilter(filter, null).getContent();
    }

    @Then("I find the lab report")
    public void i_find_the_lab_report() {
        assertTrue(labReportSearchResults.size() > 0);
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
                filter.setProgramAreas(Arrays.asList("STD"));
                break;
            case "jurisdiction":
                filter.setJurisdictions(Arrays.asList(EventMother.CLAYTON_CODE));
                break;
            case "pregnancy status":
                filter.setPregnancyStatus(PregnancyStatus.YES);
                break;
            case "event id":
                switch (qualifier) {
                    case "accession number":
                        filter.setEventIdType(LaboratoryEventIdType.ACCESSION_NUMBER);
                        filter.setEventId("accession number");
                        break;
                    case "lab id":
                        filter.setEventIdType(LaboratoryEventIdType.LAB_ID);
                        filter.setEventId("OBS10003024GA01");
                        break;
                }
                break;
            case "event date":
                var eds = new LaboratoryEventDateSearch();
                eds.setEventDateType(LabReportDateType.valueOf(qualifier));
                eds.setFrom(Instant.now().minus(5, ChronoUnit.DAYS));
                eds.setTo(Instant.now());
                filter.setEventDateSearch(eds);
                break;
            case "entry method":
                filter.setEntryMethods(Arrays.asList(EntryMethod.ELECTRONIC));
                filter.setEnteredBy(Arrays.asList(UserType.EXTERNAL));
                break;
            case "entered by":
                filter.setEnteredBy(Arrays.asList(UserType.EXTERNAL));
                break;
            case "event status":
                filter.setEventStatus(Arrays.asList(EventStatus.NEW));
                break;
            case "processing status":
                filter.setProcessingStatus(Arrays.asList(ProcessingStatus.UNPROCESSED));
                break;
            case "created by":
                filter.setCreatedBy(EventMother.CREATED_BY);
                break;
            case "last updated by":
                filter.setLastUpdatedBy(EventMother.UPDATED_BY);
                break;
            case "provider search":
                var ps = new LabReportProviderSearch();
                ps.setProviderType(ProviderType.valueOf(qualifier));
                ps.setProviderId(PATIENT_ID);
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
