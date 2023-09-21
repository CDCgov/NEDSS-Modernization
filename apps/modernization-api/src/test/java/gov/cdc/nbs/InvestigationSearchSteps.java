package gov.cdc.nbs;

import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.event.search.InvestigationFilter.CaseStatus;
import gov.cdc.nbs.event.search.InvestigationFilter.EventDate;
import gov.cdc.nbs.event.search.InvestigationFilter.EventDateType;
import gov.cdc.nbs.event.search.InvestigationFilter.IdType;
import gov.cdc.nbs.event.search.InvestigationFilter.InvestigationEventId;
import gov.cdc.nbs.event.search.InvestigationFilter.NotificationStatus;
import gov.cdc.nbs.event.search.InvestigationFilter.ProcessingStatus;
import gov.cdc.nbs.event.search.investigation.InvestigationResolver;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;
import gov.cdc.nbs.repository.elasticsearch.InvestigationRepository;
import gov.cdc.nbs.support.EventMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


@Transactional
public class InvestigationSearchSteps {
    @Autowired
    InvestigationResolver investigationResolver;
    @Autowired
    InvestigationRepository investigationRepository;
    @Autowired
    JurisdictionCodeRepository jurisdictionCodeRepository;
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

    @Given("An Investigation with a {string} status of {string} exists")
    public void an_investigation_with_field_set_to_status_exists(final String field, final String status) {
        addJurisdictionEntries();
        var investigation = EventMother.investigation_bacterialVaginosis(personId);
        switch (field) {
            case "processing":
                investigation.setCurrProcessStateCd(ProcessingStatus.valueOf(status).value());
                break;
            case "notification":
                investigation.setNotificationRecordStatusCd(NotificationStatus.valueOf(status).value());
                break;
            case "case":
                investigation.setCaseClassCd(CaseStatus.valueOf(status).value());
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
        investigationRepository.save(investigation);
    }

    @When("I search for an investigation with a {string} status of {string}")
    public void i_search_for_an_investigation_with_field_of_status(String field, String status) {
        var filter = new InvestigationFilter();
        switch (field) {
            case "processing":
                filter.getProcessingStatuses().add(ProcessingStatus.valueOf(status));
                break;
            case "notification":
                filter.getNotificationStatuses().add(NotificationStatus.valueOf(status));
                break;
            case "case":
                filter.getCaseStatuses().add(CaseStatus.valueOf(status));
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
        assertThat(investigationSearchResults).isNotEmpty();
    }

    @Then("I find investigations with only a {string} status of {string}")
    public void i_find_the_investigations_with_field_and_status(final String field, final String statusString) {

        Consumer<Investigation> assertion = switch (field) {
            case "processing" -> investigation -> assertThat(investigation.getCurrProcessStateCd())
                .isEqualTo(ProcessingStatus.valueOf(statusString).value());

            case "notification" -> investigation -> assertThat(investigation.getNotificationRecordStatusCd())
                .isEqualTo(NotificationStatus.valueOf(statusString).value());

            case "case" -> investigation -> assertThat(investigation.getCaseClassCd())
                .isEqualTo(CaseStatus.valueOf(statusString).value());

            default -> investigation -> fail();
        };

        assertThat(investigationSearchResults).allSatisfy(assertion);
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
                filter.setConditions(Collections.singletonList(qualifier));
                break;
            case "program area":
                filter.setProgramAreas(Collections.singletonList(qualifier));
                break;
            case "jurisdiction":
                if (qualifier.equals("jd1")) {
                    filter.setJurisdictions((Collections.singletonList(EventMother.DEKALB_CODE)));
                } else {
                    filter.setJurisdictions(Collections.singletonList(EventMother.CLAYTON_CODE));
                }
                break;
            case "pregnancy status":
                filter.setPregnancyStatus(PregnancyStatus.YES);
                break;
            case "event id":
                IdType type = IdType.valueOf(qualifier);
                switch (type) {
                    case ABCS_CASE_ID:
                        filter.setEventId(new InvestigationEventId(type, "CityTypeRootExtensionText"));
                        break;
                    case CITY_COUNTY_CASE_ID:
                        filter.setEventId(new InvestigationEventId(type, "CityTypeRootExtensionText"));
                        break;
                    case INVESTIGATION_ID:
                        filter.setEventId(new InvestigationEventId(type, "CAS10001000GA01"));
                        break;
                    case NOTIFICATION_ID:
                        filter.setEventId(new InvestigationEventId(type, "notificationLocalId"));
                        break;
                    case STATE_CASE_ID:
                        filter.setEventId(new InvestigationEventId(type, "StateRootExtensionText"));
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid event id type specified: " +
                            qualifier);
                }
                break;
            case "event date":
                filter.setEventDate(new EventDate(
                    EventDateType.valueOf(qualifier),
                    LocalDate.now().minusDays(5),
                    LocalDate.now().plusDays(2)));
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
