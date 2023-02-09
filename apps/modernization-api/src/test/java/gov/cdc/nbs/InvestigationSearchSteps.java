package gov.cdc.nbs;

import static org.junit.Assert.assertTrue;

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
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.graphql.filter.InvestigationFilter;
import gov.cdc.nbs.graphql.filter.InvestigationFilter.IdType;
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
    private EventController eventController;
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

    @When("I search investigation events by {string} {string}")
    public void i_search_patients_by_investigation_events(String field, String qualifier) {
        var filter = updateInvestigationFilter(new InvestigationFilter(), field, qualifier);
        investigationSearchResults = eventController.findInvestigationsByFilter(filter, null).getContent();
    }

    @When("I search investigation events by {string} {string} {string} {string} {string} {string}")
    public void i_search_patients_by_investigation_events(String field, String qualifier, String field2,
            String qualifier2, String field3, String qualifier3) {
        InvestigationFilter filter = updateInvestigationFilter(new InvestigationFilter(), field, qualifier);
        updateInvestigationFilter(filter, field2, qualifier2);
        updateInvestigationFilter(filter, field3, qualifier3);
        investigationSearchResults = eventController.findInvestigationsByFilter(filter, null).getContent();

    }

    @Then("I find the investigation")
    public void i_find_the_investigation() {
        assertTrue(investigationSearchResults.size() > 0);
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
                filter.setEventIdType(IdType.valueOf(qualifier));
                switch (filter.getEventIdType()) {
                    case ABCS_CASE_ID:
                        filter.setEventId("CityTypeRootExtensionText");
                        break;
                    case CITY_COUNTY_CASE_ID:
                        filter.setEventId("CityTypeRootExtensionText");
                        break;
                    case INVESTIGATION_ID:
                        filter.setEventId("CAS10001000GA01");
                        break;
                    case NOTIFICATION_ID:
                        filter.setEventId("notificationLocalId");
                        break;
                    case STATE_CASE_ID:
                        filter.setEventId("StateRootExtensionText");
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid event id qualifer specified: " +
                                qualifier);
                }
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
