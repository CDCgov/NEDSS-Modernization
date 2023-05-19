package gov.cdc.nbs.patientlistener.request.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.patientlistener.request.UserNotAuthorizedException;
import gov.cdc.nbs.patientlistener.request.PatientRequestStatusProducer;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;

class PatientCreateRequestHandlerTest {

    @Mock
    private UserService userService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private ElasticsearchPersonRepository elasticsearchPersonRepository;
    @Mock
    private PatientRequestStatusProducer producer;

    @InjectMocks
    private PatientCreateRequestHandler patientService;

    @Captor
    ArgumentCaptor<Person> personCaptor;
    @Captor
    ArgumentCaptor<ElasticsearchPerson> elasticsearchPersonCaptor;

    @Spy
    private final ObjectMapper mapper =
            new ObjectMapper().setSerializationInclusion(Include.NON_NULL).registerModule(new JavaTimeModule());

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SuppressWarnings("squid:S5961")
    // Allow more than 25 assertions
    void should_create_patient_in_database_and_elasticsearch() throws JsonProcessingException {
        // Mock methods

        doReturn(true).when(userService).isAuthorized(269L, "FIND-PATIENT", "ADD-PATIENT");

        when(personRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        patientService.creator = new PatientCreator(personRepository);

        String json = """
                {
                    "request": "RequestId",
                    "patient": 191,
                    "patientLocalId": "PSN123GA01",
                    "ssn": "SSN",
                    "dateOfBirth": "2023-01-23",
                    "birthGender": "M",
                    "currentGender": "F",
                    "deceased": "N",
                    "maritalStatus": "Marital Status",
                    "names": [
                        {
                            "first": "First",
                            "middle": "Middle",
                            "last": "Last",
                            "suffix": "JR",
                            "use": "L"
                        },
                        {
                            "first": "Second",
                            "middle": "SecondMiddle",
                            "last": "SecondLast",
                            "suffix": "SR",
                            "use": "AL"
                        }
                    ],
                    "ethnicity": "EthCode",
                    "races": [
                        "Race Code1",
                        "Race Code2"
                    ],
                    "addresses": [
                        {
                            "id" : 5953,
                            "streetAddress1": "SA1",
                            "streetAddress2": "SA2",
                            "city": "City",
                            "stateCode": "State",
                            "countyCode": "County",
                            "countryCode": "Country",
                            "zip": "Zip",
                            "censusTract": "Census Tract"
                        }
                    ],
                    "phoneNumbers": [
                        {
                            "id": 5801,
                            "number": "Phone Number",
                            "extension": "Extension",
                            "phoneType": "CELL"
                        }
                    ],
                    "emailAddresses": [
                        {
                            "id" : 5099,
                            "emailAddress" : "AnEmail@email.com"
                        }
                    ],
                    "createdBy": 269,
                    "createdAt": 1676654454.121805000,
                    "asOf": 1564494454.110914000,
                    "comments": "comments"
                }
                """;
        var data = mapper.readValue(json, PatientCreateData.class);

        // call handlePatientCreate
        patientService.handlePatientCreate(data);

        // verify proper data saved to repositories
        verify(personRepository).save(personCaptor.capture());
        verify(elasticsearchPersonRepository).save(elasticsearchPersonCaptor.capture());

        Person actual_person = personCaptor.getValue();

        assertThat(actual_person.getStatusCd()).isEqualTo('A');
        assertThat(actual_person.getRecordStatusCd()).isEqualTo(RecordStatus.ACTIVE);
        assertThat(actual_person.getSsn()).isEqualTo("SSN");
        assertThat(LocalDate.ofInstant(actual_person.getBirthTime(), ZoneId.systemDefault())).isEqualTo("2023-01-23");
        assertThat(actual_person.getBirthGenderCd()).isEqualTo(Gender.M);
        assertThat(actual_person.getCurrSexCd()).isEqualTo(Gender.F);
        assertThat(actual_person.getDeceasedIndCd()).isEqualTo(Deceased.N);
        assertThat(actual_person.getEthnicGroupInd()).isEqualTo("EthCode");
        assertThat(actual_person.getAsOfDateGeneral()).isEqualTo("2019-07-30T13:47:34.110914Z");
        assertThat(actual_person.getAsOfDateAdmin()).isEqualTo("2019-07-30T13:47:34.110914Z");
        assertThat(actual_person.getAsOfDateSex()).isEqualTo("2019-07-30T13:47:34.110914Z");
        assertThat(actual_person.getDescription()).isEqualTo("comments");

        assertThat(actual_person.getNames()).satisfiesExactly(
                actual_primary -> assertThat(actual_primary).returns("First", PersonName::getFirstNm)
                        .returns("Middle", PersonName::getMiddleNm).returns("Last", PersonName::getLastNm)
                        .returns(Suffix.JR, PersonName::getNmSuffix).returns("L", PersonName::getNmUseCd),
                actual_alias -> assertThat(actual_alias).returns("Second", PersonName::getFirstNm)
                        .returns("SecondMiddle", PersonName::getMiddleNm).returns("SecondLast", PersonName::getLastNm)
                        .returns(Suffix.SR, PersonName::getNmSuffix).returns("AL", PersonName::getNmUseCd));

        assertThat(actual_person.getRaces()).satisfiesExactly(
                actual_race -> assertThat(actual_race).returns("Race Code1", PersonRace::getRaceCd),
                actual_race -> assertThat(actual_race).returns("Race Code2", PersonRace::getRaceCd));

        assertThat(actual_person.getNbsEntity().getEntityLocatorParticipations()).satisfiesExactlyInAnyOrder(
                actual_phone_locator -> assertThat(actual_phone_locator).isInstanceOf(
                        TeleEntityLocatorParticipation.class)
                        .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                        .returns("CP", EntityLocatorParticipation::getCd)
                        .extracting(TeleEntityLocatorParticipation::getLocator).returns(5801L, TeleLocator::getId)
                        .returns("Phone Number", TeleLocator::getPhoneNbrTxt)
                        .returns("Extension", TeleLocator::getExtensionTxt),
                actual_email_locator -> assertThat(actual_email_locator).isInstanceOf(
                        TeleEntityLocatorParticipation.class)
                        .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                        .returns("NET", EntityLocatorParticipation::getCd)
                        .extracting(TeleEntityLocatorParticipation::getLocator).returns(5099L, TeleLocator::getId)
                        .returns("AnEmail@email.com", TeleLocator::getEmailAddress),
                actual_postal_locator -> assertThat(actual_postal_locator).isInstanceOf(
                        PostalEntityLocatorParticipation.class)
                        .asInstanceOf(InstanceOfAssertFactories.type(PostalEntityLocatorParticipation.class))
                        .returns("H", EntityLocatorParticipation::getCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator).returns(5953L, PostalLocator::getId)
                        .returns("SA1", PostalLocator::getStreetAddr1).returns("SA2", PostalLocator::getStreetAddr2)
                        //  should this be getCityDescTxt or getCityCd?
                        .returns("City", PostalLocator::getCityCd).returns("State", PostalLocator::getStateCd)
                        .returns("County", PostalLocator::getCntyCd).returns("Zip", PostalLocator::getZipCd)
                        .returns("Country", PostalLocator::getCntryCd)

        );

        verifyElasticsearchPerson(elasticsearchPersonCaptor.getValue(), actual_person);

        // verify successful patient create status was posted to topic
        verify(producer).successful(
                "RequestId",
                "Successfully created patient",
                191L);
    }

    @Test
    void should_not_create_patient_if_requester_lacks_permission()
            throws JsonProcessingException {
        // set invalid user credentials
        doReturn(false).when(userService).isAuthorized(anyLong(), any(), any());

        String json = """
                {
                    "request": "RequestId",
                    "patient": 191,
                    "patientLocalId": "PSN123GA01"
                }
                """;

        var data = mapper.readValue(json, PatientCreateData.class);

        // Send the request
        UserNotAuthorizedException ex = null;
        try {
            patientService.handlePatientCreate(data);
        } catch (UserNotAuthorizedException e) {
            ex = e;
        }

        // Verify exception is thrown
        assertNotNull(ex);

        // Verify nothing was saved
        verify(personRepository, never()).save(Mockito.any());
        verify(elasticsearchPersonRepository, never()).save(Mockito.any());

    }

    private void verifyElasticsearchPerson(ElasticsearchPerson esPerson, Person person) {
        assertEquals(person.getId().toString(), esPerson.getId());
        assertEquals(person.getId(), esPerson.getPersonUid());
        assertEquals(person.getFirstNm(), esPerson.getFirstNm());
        assertEquals(person.getLastNm(), esPerson.getLastNm());
        assertEquals(person.getMiddleNm(), esPerson.getMiddleNm());
        assertEquals(person.getAddUserId(), esPerson.getAddUserId());
        assertEquals(person.getBirthTime(), esPerson.getBirthTime());
        assertEquals(person.getCd(), esPerson.getCd());
        assertEquals(person.getCurrSexCd(), esPerson.getCurrSexCd());
        assertEquals(person.getDeceasedIndCd(), esPerson.getDeceasedIndCd());
        assertEquals(person.getDeceasedTime(), esPerson.getDeceasedTime());
        assertEquals(person.getElectronicInd(), esPerson.getElectronicInd());
        assertEquals(person.getEthnicGroupInd(), esPerson.getEthnicGroupInd());
        assertEquals(person.getMaritalStatusCd(), esPerson.getMaritalStatusCd());
        assertEquals(person.getRecordStatusCd(), esPerson.getRecordStatusCd());
        assertEquals(person.getLocalId(), esPerson.getLocalId());
        assertEquals(person.getVersionCtrlNbr(), esPerson.getVersionCtrlNbr());
        assertEquals(person.getDedupMatchInd(), esPerson.getDedupMatchInd());
        assertEquals(person.getAsOfDateAdmin(), esPerson.getAsOfDateAdmin());
        assertEquals(person.getAsOfDateGeneral(), esPerson.getAsOfDateGeneral());
        assertEquals(person.getAsOfDateSex(), esPerson.getAsOfDateSex());
        assertEquals(person.getDescription(), esPerson.getDescription());

        // Addresses
        var personAddresses = person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(PostalEntityLocatorParticipation.class::isInstance)
                .map(elp -> ((PostalLocator) elp.getLocator())).toList();

        for (int i = 0; i < personAddresses.size(); i++) {
            var pa = personAddresses.get(i);
            var matchingRecord = esPerson.getAddress().stream().filter(esAddress -> esAddress.getStreetAddr1()
                    .equals(pa.getStreetAddr1())
                    && esAddress.getStreetAddr2()
                            .equals(pa.getStreetAddr2())
                    && esAddress.getCity().equals(pa.getCityCd()) && esAddress.getState()
                            .equals(pa.getStateCd())
                    && esAddress.getCntyCd().equals(pa.getCntyCd()) && esAddress.getZip()
                            .equals(pa.getZipCd())
                    && esAddress.getCntryCd().equals(pa.getCntryCd())).findFirst();
            assertTrue(matchingRecord.isPresent());
        }

        // Phone numbers
        var personPhones = person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .filter(elp -> !Objects.equals(elp.getCd(), "NET")).map(elp -> ((TeleLocator) elp.getLocator()))
                .toList();

        for (int i = 0; i < personPhones.size(); i++) {
            var pp = personPhones.get(i);
            var matchingRecord = esPerson.getPhone().stream().filter(esPhone -> esPhone.getTelephoneNbr()
                    .equals(pp.getPhoneNbrTxt()) && esPhone.getExtensionTxt().equals(pp.getExtensionTxt())).findFirst();
            assertTrue(matchingRecord.isPresent());
        }

        // Email addresses
        var personEmails = person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .filter(elp -> Objects.equals(elp.getCd(), "NET")).map(elp -> ((TeleLocator) elp.getLocator()))
                .toList();

        for (int i = 0; i < personEmails.size(); i++) {
            var pe = personEmails.get(i);
            var matchingRecord = esPerson.getEmail().stream()
                    .filter(esEmail -> esEmail.getEmailAddress().equals(pe.getEmailAddress())).findFirst();
            assertTrue(matchingRecord.isPresent());
        }

        // Names
        var personNames = person.getNames();
        for (int i = 0; i < personNames.size(); i++) {
            var pn = personNames.get(i);
            var matchingRecord = esPerson.getName().stream()
                    .filter(esName -> esName.getFirstNm().equals(pn.getFirstNm()) && esName.getMiddleNm()
                            .equals(pn.getMiddleNm())
                            && esName.getLastNm()
                                    .equals(pn.getLastNm())
                            && esName.getNmSuffix().equals(pn.getNmSuffix().toString()))
                    .findFirst();
            assertTrue(matchingRecord.isPresent());
        }

        // Races
        var personRaces = person.getRaces();
        for (int i = 0; i < personRaces.size(); i++) {
            var pr = personRaces.get(i);
            var matchingRecord =
                    esPerson.getRace().stream().filter(esRace -> esRace.getRaceCd().equals(pr.getRaceCategoryCd()))
                            .findFirst();
            assertTrue(matchingRecord.isPresent());
        }
    }

}
