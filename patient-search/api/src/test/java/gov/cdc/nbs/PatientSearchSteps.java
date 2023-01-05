package gov.cdc.nbs;

import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.controller.PatientController;
import gov.cdc.nbs.entity.enums.PregnancyStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.searchFilter.EventFilter;
import gov.cdc.nbs.graphql.searchFilter.EventFilter.EventType;
import gov.cdc.nbs.graphql.searchFilter.InvestigationFilter;
import gov.cdc.nbs.graphql.searchFilter.InvestigationFilter.IdType;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.EntryMethod;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.EventStatus;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.LabReportDateType;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.LabReportProviderSearch;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.LaboratoryEventDateSearch;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.LaboratoryEventIdType;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.ProcessingStatus;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.ProviderType;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.UserType;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter.Identification;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.repository.elasticsearch.InvestigationRepository;
import gov.cdc.nbs.repository.elasticsearch.LabReportRepository;
import gov.cdc.nbs.support.EventMother;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.util.PersonUtil;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class PatientSearchSteps {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PostalLocatorRepository postalLocatorRepository;
    @Autowired
    private TeleLocatorRepository teleLocatorRepository;
    @Autowired
    private PatientController patientController;
    @Autowired
    private InvestigationRepository investigationRepository;
    @Autowired
    private LabReportRepository labReportRepository;
    @Autowired
    private JurisdictionCodeRepository jurisdictionCodeRepository;

    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    private Person searchPatient;
    private List<Person> searchResults;
    private List<Person> generatedPersons;

    @Before
    public void clearAuth() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Given("there are {int} patients")
    public void there_are_patients(int patientCount) {
        // person data is randomly generated but the Ids are always the same.
        generatedPersons = PersonMother.getRandomPersons(patientCount);

        var generatedIds = generatedPersons.stream()
                .map(p -> p.getId()).collect(Collectors.toList());
        // find existing persons
        var existingPersons = personRepository.findAllById(generatedIds);
        // delete existing locator entries
        var existingPostal = PersonUtil.getPostalLocators(existingPersons);
        postalLocatorRepository.deleteAll(existingPostal);
        var existingTele = PersonUtil.getTeleLocators(existingPersons);
        teleLocatorRepository.deleteAll(existingTele);
        // delete existing persons
        personRepository.deleteAll(existingPersons);

        // create new persons
        teleLocatorRepository.saveAll(PersonUtil.getTeleLocators(generatedPersons));
        postalLocatorRepository.saveAll(PersonUtil.getPostalLocators(generatedPersons));
        personRepository.saveAll(generatedPersons);
        elasticsearchPersonRepository.saveAll(PersonUtil.getElasticSearchPersons(generatedPersons));
    }

    @Given("Investigations exist")
    public void investigations_exist() {
        addJurisdictionEntries();
        if (searchPatient == null) {
            searchPatient = RandomUtil.getRandomFromArray(generatedPersons);
        }
        var investigation1 = EventMother.investigation_bacterialVaginosis(searchPatient.getId());
        investigationRepository.save(investigation1);
        var investigation2 = EventMother.investigation_trichomoniasis(searchPatient.getId());
        investigationRepository.save(investigation2);
    }

    @Given("A lab report exist")
    public void lab_report_exist() {
        addJurisdictionEntries();
        if (searchPatient == null) {
            searchPatient = RandomUtil.getRandomFromArray(generatedPersons);
        }
        var labReportEntries = EventMother.labReport_acidFastStain(searchPatient.getId());
        labReportRepository.saveAll(labReportEntries);
    }

    private void addJurisdictionEntries() {
        var jurisdictions = EventMother.getJurisdictionCodes();
        jurisdictionCodeRepository.saveAll(jurisdictions);
    }

    @Given("I am looking for one of them")
    public void I_am_looking_for_one_of_them() {
        // pick one of the existing patients at random
        var index = RandomUtil.getRandomInt(generatedPersons.size());
        searchPatient = generatedPersons.get(index);
    }

    @When("I search patients by {string} {string}")
    public void i_search_patients_by_field(String field, String qualifier) {
        PatientFilter filter = getPatientDataFilter(field, qualifier);
        searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(1000, 0)).getContent();
    }

    @When("I search patients by {string} {string} {string} {string} {string} {string}")
    public void i_search_patients_by_multiple_fields(String field, String qualifier, String field2, String qualifier2,
            String field3, String qualifier3) {
        PatientFilter filter = getPatientDataFilter(field, qualifier);
        updatePatientDataFilter(filter, field2, qualifier2);
        updatePatientDataFilter(filter, field3, qualifier3);
        searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(1000, 0)).getContent();
    }

    @When("I search patients using partial data {string} {string} {string} {string}")
    public void i_search_patients_using_partial_data(String field, String qualifier, String field2, String qualifier2) {
        PatientFilter filter = getPatientPartialDataFilter(field, qualifier);
        updatePatientPartialDataFilter(filter, field2, qualifier2);
        searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(1000, 0)).getContent();
    }

    @When("I search investigation events by {string} {string}")
    public void i_search_patients_by_investigation_events(String field, String qualifier) {
        EventFilter filter = getInvestigationFilter(field, qualifier);
        searchResults = patientController.findPatientsByEvent(filter, null).getContent();
    }

    @When("I search investigation events by {string} {string} {string} {string} {string} {string}")
    public void i_search_patients_by_investigation_events(String field, String qualifier, String field2,
            String qualifier2, String field3, String qualifier3) {
        EventFilter filter = getInvestigationFilter(field, qualifier);
        updateInvestigationFilter(filter, field2, qualifier2);
        updateInvestigationFilter(filter, field3, qualifier3);
        searchResults = patientController.findPatientsByEvent(filter, null).getContent();
    }

    @When("I search laboratory events by {string} {string}")
    public void i_search_patients_by_laboratory_events(String field, String qualifier) {
        EventFilter filter = getLabReportFilter(field, qualifier);
        searchResults = patientController.findPatientsByEvent(filter, null).getContent();
    }

    @When("I search laboratory events by {string} {string} {string} {string} {string} {string}")
    public void i_search_patients_by_laboratory_events(String field, String qualifier, String field2, String qualifier2,
            String field3, String qualifier3) {
        EventFilter filter = getLabReportFilter(field, qualifier);
        updateLabReportFilter(filter, field2, qualifier2);
        updateLabReportFilter(filter, field3, qualifier3);
        searchResults = patientController.findPatientsByEvent(filter, null).getContent();
    }

    @Then("I find the patient")
    public void i_find_the_patient() {
        assertTrue("Search patient is not null", searchPatient != null);
        assertTrue("Search results are not empty", searchResults.size() > 0);
        assertTrue("Search results contains patient: " + searchPatient.getId(), searchResults.contains(searchPatient));
    }

    private EventFilter getLabReportFilter(String field, String qualifier) {
        var filter = new EventFilter();
        filter.setLaboratoryReportFilter(new LaboratoryReportFilter());
        return updateLabReportFilter(filter, field, qualifier);
    }

    private EventFilter updateLabReportFilter(EventFilter filter, String field, String qualifier) {
        if (field == null || field.isEmpty()) {
            return filter;
        }
        filter.setEventType(EventType.LABORATORY_REPORT);
        var criteria = filter.getLaboratoryReportFilter();
        switch (field) {
            case "program area":
                criteria.setProgramAreas(Arrays.asList("STD"));
                break;
            case "jurisdiction":
                criteria.setJurisdictions(Arrays.asList(EventMother.CLAYTON_CODE));
                break;
            case "pregnancy status":
                criteria.setPregnancyStatus(PregnancyStatus.YES);
                break;
            case "event id":
                switch (qualifier) {
                    case "accession number":
                        criteria.setEventIdType(LaboratoryEventIdType.ACCESSION_NUMBER);
                        criteria.setEventId("accession number");
                        break;
                    case "lab id":
                        criteria.setEventIdType(LaboratoryEventIdType.LAB_ID);
                        criteria.setEventId("OBS10003024GA01");
                        break;
                }
                break;
            case "event date":
                var eds = new LaboratoryEventDateSearch();
                eds.setEventDateType(LabReportDateType.valueOf(qualifier));
                eds.setFrom(Instant.now().minus(5, ChronoUnit.DAYS));
                eds.setTo(Instant.now());
                criteria.setEventDateSearch(eds);
                break;
            case "entry method":
                criteria.setEntryMethods(Arrays.asList(EntryMethod.ELECTRONIC));
                criteria.setEnteredBy(Arrays.asList(UserType.EXTERNAL));
                break;
            case "entered by":
                criteria.setEnteredBy(Arrays.asList(UserType.EXTERNAL));
                break;
            case "event status":
                criteria.setEventStatus(Arrays.asList(EventStatus.NEW));
                break;
            case "processing status":
                criteria.setProcessingStatus(Arrays.asList(ProcessingStatus.UNPROCESSED));
                break;
            case "created by":
                criteria.setCreatedBy(EventMother.CREATED_BY);
                break;
            case "last updated by":
                criteria.setLastUpdatedBy(EventMother.UPDATED_BY);
                break;
            case "provider search":
                var ps = new LabReportProviderSearch();
                ps.setProviderType(ProviderType.valueOf(qualifier));
                ps.setProviderId(searchPatient.getId());
                break;
            case "resulted test":
                criteria.setResultedTest("Acid-Fast Stain");
                break;
            case "coded result":
                criteria.setCodedResult("abnormal");
                break;
            default:
                throw new IllegalArgumentException("Unsupported field: " + field);
        }

        return filter;
    }

    private EventFilter getInvestigationFilter(String field, String qualifier) {
        var filter = new EventFilter();
        return updateInvestigationFilter(filter, field, qualifier);
    }

    private EventFilter updateInvestigationFilter(EventFilter filter, String field, String qualifier) {
        if (field == null || field.isEmpty()) {
            return filter;
        }
        filter.setEventType(EventType.INVESTIGATION);
        filter.setInvestigationFilter(new InvestigationFilter());
        var criteria = filter.getInvestigationFilter();
        switch (field) {
            case "condition":
                criteria.setConditions(Arrays.asList(qualifier));
                break;
            case "program area":
                criteria.setProgramAreas(Arrays.asList(qualifier));
                break;
            case "jurisdiction":
                if (qualifier.equals("jd1")) {
                    criteria.setJurisdictions((Arrays.asList(EventMother.DEKALB_CODE)));
                } else {
                    criteria.setJurisdictions(Arrays.asList(EventMother.CLAYTON_CODE));
                }
                break;
            case "pregnancy status":
                criteria.setPregnancyStatus(PregnancyStatus.YES);
                break;
            case "event id":
                criteria.setEventIdType(IdType.valueOf(qualifier));
                switch (criteria.getEventIdType()) {
                    case ABCS_CASE_ID:
                        criteria.setEventId("CityTypeRootExtensionText");
                        break;
                    case CITY_COUNTY_CASE_ID:
                        criteria.setEventId("CityTypeRootExtensionText");
                        break;
                    case INVESTIGATION_ID:
                        criteria.setEventId("CAS10001000GA01");
                        break;
                    case NOTIFICATION_ID:
                        criteria.setEventId("notificationLocalId");
                        break;
                    case STATE_CASE_ID:
                        criteria.setEventId("StateRootExtensionText");
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid event id qualifer specified: " +
                                qualifier);
                }
                break;
            case "created by":
                criteria.setCreatedBy(EventMother.CREATED_BY);
                break;
            case "updated by":
                criteria.setLastUpdatedBy(EventMother.UPDATED_BY);
                break;
            default:
                throw new IllegalArgumentException("Unsupported field: " + field);
        }
        return filter;
    }

    private PatientFilter updatePatientDataFilter(PatientFilter filter, String field, String qualifier) {
        if (field == null || field.isEmpty()) {
            return filter;
        }
        switch (field) {
            case "last name":
                filter.setLastName(searchPatient.getLastNm());
                break;
            case "first name":
                filter.setFirstName(searchPatient.getFirstNm());
                break;
            case "race":
                filter.setRace(searchPatient.getRaces().get(0).getId().getRaceCd());
                break;
            case "identification":
                var patientId = searchPatient.getEntityIds().get(0);
                filter.setIdentification(new Identification(patientId.getRootExtensionTxt(), patientId.getTypeCd()));
                break;
            case "patient id":
                filter.setId(searchPatient.getId());
                break;
            case "ssn":
                filter.setSsn(searchPatient.getSsn());
                break;
            case "phone number":
                var teleLocator = PersonUtil.getTeleLocators(searchPatient).get(0);
                filter.setPhoneNumber(teleLocator.getPhoneNbrTxt());
                break;
            case "date of birth":
                filter.setDateOfBirth(getDobByQualifier(searchPatient, qualifier));
                filter.setDateOfBirthOperator(qualifier);
                break;
            case "gender":
                filter.setGender(searchPatient.getBirthGenderCd());
                break;
            case "deceased":
                filter.setDeceased(searchPatient.getDeceasedIndCd());
                break;
            case "address":
                var addressLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setAddress(addressLocator.getStreetAddr1());
                break;
            case "city":
                var cityLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setCity(cityLocator.getCityCd());
                break;
            case "state":
                var stateLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setState(stateLocator.getStateCd());
                break;
            case "country":
                var cntryLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setCountry(cntryLocator.getCntryCd());
                break;
            case "zip code":
                var zipLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setZip(zipLocator.getZipCd());
                break;
            case "ethnicity":
                filter.setEthnicity(searchPatient.getEthnicGroupInd());
                break;
            case "record status":
                filter.setRecordStatus(searchPatient.getRecordStatusCd());
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
        return filter;
    }

    private PatientFilter updatePatientPartialDataFilter(PatientFilter filter, String field, String qualifier) {
        if (field == null || field.isEmpty()) {
            return filter;
        }
        switch (field) {
            case "last name":
                filter.setLastName(RandomUtil.randomPartialDataSearchString(searchPatient.getLastNm()));
                break;
            case "first name":
                filter.setFirstName(RandomUtil.randomPartialDataSearchString(searchPatient.getFirstNm()));
                break;
            case "address":
                var addressLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setAddress(RandomUtil.randomPartialDataSearchString(addressLocator.getStreetAddr1()));
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
        return filter;
    }

    private PatientFilter getPatientDataFilter(String field, String qualifier) {
        var filter = new PatientFilter();
        return updatePatientDataFilter(filter, field, qualifier);
    }

    private PatientFilter getPatientPartialDataFilter(String field, String qualifier) {
        var filter = new PatientFilter();
        return updatePatientPartialDataFilter(filter, field, qualifier);
    }

    private Instant getDobByQualifier(Person search, String qualifier) {
        switch (qualifier) {
            case "before":
                return search.getBirthTime().plus(15, ChronoUnit.DAYS);
            case "after":
                return search.getBirthTime().minus(15, ChronoUnit.DAYS);
            case "equal":
                return search.getBirthTime();
            default:
                throw new IllegalArgumentException("Invalid date of birth qualifier: " + qualifier);
        }
    }

}