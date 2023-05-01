package gov.cdc.nbs;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
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
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.investigation.InvestigationFilter;
import gov.cdc.nbs.investigation.InvestigationFilter.CaseStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.EventDate;
import gov.cdc.nbs.investigation.InvestigationFilter.EventDateType;
import gov.cdc.nbs.investigation.InvestigationFilter.EventId;
import gov.cdc.nbs.investigation.InvestigationFilter.IdType;
import gov.cdc.nbs.investigation.InvestigationFilter.NotificationStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.ProcessingStatus;
import gov.cdc.nbs.investigation.InvestigationResolver;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;
import gov.cdc.nbs.repository.elasticsearch.InvestigationRepository;
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
public class InvestigationSearchSteps {
    @Autowired
    private InvestigationResolver investigationResolver;
    @Autowired
    private InvestigationRepository investigationRepository;
    @Autowired
    private JurisdictionCodeRepository jurisdictionCodeRepository;
    private List<Investigation> investigationSearchResults;

    private static final Long personId = 9999999L;

    @Given("Investigations exist")
    public void investigations_exist() {
        addJurisdictionEntries();
        var investigation1 = EventMother.investigation_bacterialVaginosis(personId);
        investigationRepository.save(investigation1);
        var investigation2 = EventMother.investigation_trichomoniasis(personId);
        investigationRepository.save(investigation2);
    }

    @Given("An Investigation with {string} set to {string} exists")
    public void an_investigation_with_field_set_to_status_exists(String field, String status) {
        addJurisdictionEntries();
        var investigation = EventMother.investigation_bacterialVaginosis(personId);
        switch (field) {
            case "processingStatus":
                setProcessingStatus(investigation, status);
                break;
            case "notificationStatus":
                setNotificationStatus(investigation, status);
                break;
            case "caseStatus":
                setCaseStatus(investigation, status);
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
        investigationRepository.save(investigation);
    }

    private void setCaseStatus(Investigation investigation, String statusString) {
        var status = CaseStatus.valueOf(statusString);
        investigation.setCaseClassCd(status.toString());
    }

    private void setNotificationStatus(Investigation investigation, String statusString) {
        var status = NotificationStatus.valueOf(statusString);
        investigation.setNotificationRecordStatusCd(status.toString());
    }

    private void setProcessingStatus(Investigation investigation, String statusString) {
        var status = ProcessingStatus.valueOf(statusString);
        investigation.setCurrProcessStateCd(status.toString());
    }

    @When("I search for an investigation with {string} of {string}")
    public void i_search_for_an_investigation_with_field_of_status(String field, String status) {
        var filter = new InvestigationFilter();
        switch (field) {
            case "processingStatus":
                filter.setProcessingStatuses(Arrays.asList(ProcessingStatus.valueOf(status)));
                break;
            case "notificationStatus":
                filter.setNotificationStatuses(Arrays.asList(NotificationStatus.valueOf(status)));
                break;
            case "caseStatus":
                filter.setCaseStatuses(Arrays.asList(CaseStatus.valueOf(status)));
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
        investigationSearchResults = investigationResolver.findInvestigationsByFilter(filter, null).getContent();
    }

    @When("I search investigation events by {string} {string}")
    public void i_search_patients_by_investigation_events(String field, String qualifier) {
        var filter = updateInvestigationFilter(new InvestigationFilter(), field, qualifier);
        investigationSearchResults = investigationResolver.findInvestigationsByFilter(filter, null).getContent();
    }

    @When("I search investigation events by {string} {string} {string} {string} {string} {string}")
    public void i_search_patients_by_investigation_events(String field, String qualifier, String field2,
            String qualifier2, String field3, String qualifier3) {
        InvestigationFilter filter = updateInvestigationFilter(new InvestigationFilter(), field, qualifier);
        updateInvestigationFilter(filter, field2, qualifier2);
        updateInvestigationFilter(filter, field3, qualifier3);
        investigationSearchResults = investigationResolver.findInvestigationsByFilter(filter, null).getContent();

    }

    @Then("I find the investigation")
    public void i_find_the_investigation() {
        assertTrue(investigationSearchResults.size() > 0);
    }

    @Then("I find investigations with {string} of {string}")
    public void i_find_the_investigations_with_field_and_status(String field, String statusString) {
        assertTrue(investigationSearchResults.size() > 0);
        switch (field) {
            case "processingStatus":
                investigationSearchResults.forEach(sr -> {
                    assertEquals(statusString, sr.getCurrProcessStateCd());
                });
                break;
            case "notificationStatus":
                investigationSearchResults.forEach(sr -> {
                    assertEquals(statusString, sr.getNotificationRecordStatusCd());
                });
                break;
            case "caseStatus":
                investigationSearchResults.forEach(sr -> {
                    assertEquals(statusString, sr.getCaseClassCd());
                });
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
    }

    private void addJurisdictionEntries() {
        var jurisdictions = EventMother.getJurisdictionCodes();
        jurisdictionCodeRepository.saveAll(jurisdictions);
    }

    private InvestigationFilter updateInvestigationFilter(InvestigationFilter filter, String field, String qualifier) {
        if (field == null || field.isEmpty()) {
            return filter;
        }
        switch (field) {
            case "condition":
                filter.setConditions(Arrays.asList(qualifier));
                break;
            case "program area":
                filter.setProgramAreas(Arrays.asList(qualifier));
                break;
            case "jurisdiction":
                if (qualifier.equals("jd1")) {
                    filter.setJurisdictions((Arrays.asList(EventMother.DEKALB_CODE)));
                } else {
                    filter.setJurisdictions(Arrays.asList(EventMother.CLAYTON_CODE));
                }
                break;
            case "pregnancy status":
                filter.setPregnancyStatus(PregnancyStatus.YES);
                break;
            case "event id":
                IdType type = IdType.valueOf(qualifier);
                switch (type) {
                    case ABCS_CASE_ID:
                        filter.setEventId(new EventId(type, "CityTypeRootExtensionText"));
                        break;
                    case CITY_COUNTY_CASE_ID:
                        filter.setEventId(new EventId(type, "CityTypeRootExtensionText"));
                        break;
                    case INVESTIGATION_ID:
                        filter.setEventId(new EventId(type, "CAS10001000GA01"));
                        break;
                    case NOTIFICATION_ID:
                        filter.setEventId(new EventId(type, "notificationLocalId"));
                        break;
                    case STATE_CASE_ID:
                        filter.setEventId(new EventId(type, "StateRootExtensionText"));
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid event id type specified: " +
                                qualifier);
                }
                break;
            case "event date":
                filter.setEventDate(new EventDate(
                        EventDateType.valueOf(qualifier),
                        LocalDate.now().minus(5, ChronoUnit.DAYS),
                        LocalDate.now().plusDays(1)));
                break;
            case "created by":
                filter.setCreatedBy(EventMother.CREATED_BY);
                break;
            case "updated by":
                filter.setLastUpdatedBy(EventMother.UPDATED_BY);
                break;
            case "patient id":
                filter.setPatientId(personId);
                break;
            default:
                throw new IllegalArgumentException("Unsupported field: " + field);
        }
        return filter;
    }
}
