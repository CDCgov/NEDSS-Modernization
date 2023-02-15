package gov.cdc.nbs.patientlistener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

import gov.cdc.nbs.config.security.NbsAuthority;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.enums.Suffix;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.PatientCreateRequest;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientInput.Identification;
import gov.cdc.nbs.message.PatientInput.Name;
import gov.cdc.nbs.message.PatientInput.NameUseCd;
import gov.cdc.nbs.message.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.PatientInput.PhoneType;
import gov.cdc.nbs.message.PatientInput.PostalAddress;
import gov.cdc.nbs.message.RequestStatus;
import gov.cdc.nbs.patientlistener.producer.KafkaProducer;
import gov.cdc.nbs.patientlistener.service.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;;

public class PatientServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private TeleLocatorRepository teleLocatorRepository;
    @Mock
    private PostalLocatorRepository postalLocatorRepository;
    @Mock
    private ElasticsearchPersonRepository elasticsearchPersonRepository;
    @Mock
    private KafkaProducer producer;
    @Mock
    private IdGeneratorService idGeneratorService;

    @InjectMocks
    private PatientService patientService;

    @Captor
    ArgumentCaptor<RequestStatus> statusCaptor;
    @Captor
    ArgumentCaptor<Person> personCaptor;
    @Captor
    ArgumentCaptor<ElasticsearchPerson> elasticsearchPersonCaptor;

    @Spy
    private final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(Include.NON_NULL)
            .registerModule(new JavaTimeModule());

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandlePatientCreate() throws Exception {
        // Mock methods
        when(userService.loadUserByUsername(Mockito.anyString())).thenReturn(validUserDetails());
        when(personRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        when(idGeneratorService.getNextValidId(Mockito.any())).thenReturn(new GeneratedId(1L, "prefix", "suffix"));

        // build create patient request
        var request = buildPatientCreateRequest();

        // call handlePatientCreate
        patientService.handlePatientCreate(mapper.writeValueAsString(request), request.getRequestId());

        // verify proper data saved to repositories
        verify(personRepository).save(personCaptor.capture());
        verify(elasticsearchPersonRepository).save(elasticsearchPersonCaptor.capture());
        verifyPersonData(personCaptor.getValue(), request.getPatientInput());
        verifyElasticsearchPerson(elasticsearchPersonCaptor.getValue(), personCaptor.getValue());

        // verify successful patient create status was posted to topic
        verify(producer).requestPatientCreateStatusEnvelope(statusCaptor.capture());
        assertTrue(statusCaptor.getValue().isSuccessful());
        assertEquals(request.getRequestId(), statusCaptor.getValue().getRequestId());
        assertEquals(personCaptor.getValue().getId(), statusCaptor.getValue().getEntityId());
    }

    @Test
    void testInvalidMessage() {
        // Send an invalid message
        patientService.handlePatientCreate("bad message", "bad key");

        // Verify error status is set
        verify(producer).requestPatientCreateStatusEnvelope(statusCaptor.capture());
        assertFalse(statusCaptor.getValue().isSuccessful());

        // Verify nothing was saved
        verify(personRepository, never()).save(Mockito.any());
        verify(elasticsearchPersonRepository, never()).save(Mockito.any());
    }

    @Test
    void testInvalidPermissions() throws JsonProcessingException {
        // set invalid user credentials
        when(userService.loadUserByUsername(Mockito.anyString())).thenReturn(invalidUserDetails());

        // build create patient request
        var request = buildPatientCreateRequest();

        // Send the request
        patientService.handlePatientCreate(mapper.writeValueAsString(request), request.getRequestId());

         // Verify error status is set
         verify(producer).requestPatientCreateStatusEnvelope(statusCaptor.capture());
         assertFalse(statusCaptor.getValue().isSuccessful());
 
         // Verify nothing was saved
         verify(personRepository, never()).save(Mockito.any());
         verify(elasticsearchPersonRepository, never()).save(Mockito.any());

    }

    private void verifyPersonData(Person person, PatientInput input) {
        assertEquals('A', person.getStatusCd());
        assertEquals(RecordStatus.ACTIVE, person.getRecordStatusCd());
        assertEquals(input.getSsn(), person.getSsn());
        assertEquals(input.getDateOfBirth(), person.getBirthTime());
        assertEquals(input.getBirthGender(), person.getBirthGenderCd());
        assertEquals(input.getCurrentGender(), person.getCurrSexCd());
        assertEquals(input.getDeceased(), person.getDeceasedIndCd());
        assertEquals(input.getEthnicityCode(), person.getEthnicGroupInd());
        var primaryName = input.getNames().get(0);
        assertEquals(primaryName.getFirstName(), person.getFirstNm());
        assertEquals(primaryName.getMiddleName(), person.getMiddleNm());
        assertEquals(primaryName.getLastName(), person.getLastNm());
        assertEquals(primaryName.getSuffix(), person.getNmSuffix());

        var alias = input.getNames().get(1);
        var personAlias = person.getNames().get(1);
        assertEquals(alias.getFirstName(), personAlias.getFirstNm());
        assertEquals(alias.getMiddleName(), personAlias.getMiddleNm());
        assertEquals(alias.getLastName(), personAlias.getLastNm());
        assertEquals(alias.getSuffix(), personAlias.getNmSuffix());
        assertEquals(alias.getNameUseCd().toString(), personAlias.getNmUseCd());

        for (int i = 0; i < input.getRaceCodes().size(); i++) {
            assertEquals(input.getRaceCodes().get(i), person.getRaces().get(i).getId().getRaceCd());
        }

        var personPhones = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getClassCd().equals("TELE")
                        && ((TeleLocator) elp.getLocator()).getPhoneNbrTxt() != null
                        && !((TeleLocator) elp.getLocator()).getPhoneNbrTxt().isEmpty())
                .map(elp -> ((TeleLocator) elp.getLocator()))
                .toList();
        for (int i = 0; i < input.getPhoneNumbers().size(); i++) {
            var inputPhone = input.getPhoneNumbers().get(i);
            var matchingRecord = personPhones.stream()
                    .filter(p -> p.getPhoneNbrTxt().equals(inputPhone.getNumber())
                            && p.getExtensionTxt().equals(inputPhone.getExtension()))
                    .findAny();
            assertTrue(matchingRecord.isPresent());
        }

        var personEmails = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getClassCd().equals("TELE")
                        && ((TeleLocator) elp.getLocator()).getEmailAddress() != null
                        && !((TeleLocator) elp.getLocator()).getEmailAddress().isEmpty())
                .map(elp -> ((TeleLocator) elp.getLocator()).getEmailAddress())
                .toList();
        for (int i = 0; i < input.getEmailAddresses().size(); i++) {
            var inputEmail = input.getEmailAddresses().get(i);
            assertTrue(personEmails.contains(inputEmail));
        }

        var personAddresses = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getClassCd().equals("PST"))
                .map(elp -> ((PostalLocator) elp.getLocator()))
                .toList();
        for (int i = 0; i < input.getAddresses().size(); i++) {
            var inputAddress = input.getAddresses().get(i);
            var matchingRecord = personAddresses.stream()
                    .filter(p -> p.getStreetAddr1().equals(inputAddress.getStreetAddress1())
                            && p.getStreetAddr2().equals(inputAddress.getStreetAddress2())
                            && p.getCityDescTxt().equals(inputAddress.getCity())
                            && p.getStateCd().equals(inputAddress.getStateCode())
                            && p.getCntyCd().equals(inputAddress.getCountyCode())
                            && p.getZipCd().equals(inputAddress.getZip())
                            && p.getCntryCd().equals(inputAddress.getCountryCode())
                            && p.getCensusTract().equals(inputAddress.getCensusTract()))
                    .findAny();
            assertTrue(matchingRecord.isPresent());
        }
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

        // Addresses
        var personAddresses = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getClassCd().equals("PST"))
                .map(elp -> ((PostalLocator) elp.getLocator()))
                .toList();

        for (int i = 0; i < personAddresses.size(); i++) {
            var pa = personAddresses.get(i);
            var matchingRecord = esPerson.getAddress().stream()
                    .filter(esAddress -> esAddress.getStreetAddr1().equals(pa.getStreetAddr1())
                            && esAddress.getStreetAddr2().equals(pa.getStreetAddr2())
                            && esAddress.getCity().equals(pa.getCityDescTxt())
                            && esAddress.getState().equals(pa.getStateCd())
                            && esAddress.getCntyCd().equals(pa.getCntyCd())
                            && esAddress.getZip().equals(pa.getZipCd())
                            && esAddress.getCntryCd().equals(pa.getCntryCd()))
                    .findFirst();
            assertTrue(matchingRecord.isPresent());
        }

        // Phone numbers
        var personPhones = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getClassCd().equals("TELE")
                        && ((TeleLocator) elp.getLocator()).getPhoneNbrTxt() != null
                        && !((TeleLocator) elp.getLocator()).getPhoneNbrTxt().isEmpty())
                .map(elp -> ((TeleLocator) elp.getLocator()))
                .toList();

        for (int i = 0; i < personPhones.size(); i++) {
            var pp = personPhones.get(i);
            var matchingRecord = esPerson.getPhone().stream()
                    .filter(esPhone -> esPhone.getTelephoneNbr().equals(pp.getPhoneNbrTxt())
                            && esPhone.getExtensionTxt().equals(pp.getExtensionTxt()))
                    .findFirst();
            assertTrue(matchingRecord.isPresent());
        }

        // Email addresses
        var personEmails = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getClassCd().equals("TELE")
                        && ((TeleLocator) elp.getLocator()).getEmailAddress() != null
                        && !((TeleLocator) elp.getLocator()).getEmailAddress().isEmpty())
                .map(elp -> ((TeleLocator) elp.getLocator()))
                .toList();

        for (int i = 0; i < personEmails.size(); i++) {
            var pe = personEmails.get(i);
            var matchingRecord = esPerson.getEmail().stream()
                    .filter(esEmail -> esEmail.getEmailAddress().equals(pe.getEmailAddress()))
                    .findFirst();
            assertTrue(matchingRecord.isPresent());
        }

        // Names
        var personNames = person.getNames();
        for (int i = 0; i < personNames.size(); i++) {
            var pn = personNames.get(i);
            var matchingRecord = esPerson.getName().stream()
                    .filter(esName -> esName.getFirstNm().equals(pn.getFirstNm())
                            && esName.getMiddleNm().equals(pn.getMiddleNm())
                            && esName.getLastNm().equals(pn.getLastNm())
                            && esName.getNmSuffix().equals(pn.getNmSuffix().toString()))
                    .findFirst();
            assertTrue(matchingRecord.isPresent());
        }

        // Races
        var personRaces = person.getRaces();
        for (int i = 0; i < personRaces.size(); i++) {
            var pr = personRaces.get(i);
            var matchingRecord = esPerson.getRace().stream()
                    .filter(esRace -> esRace.getRaceCd().equals(pr.getRaceCategoryCd()))
                    .findFirst();
            assertTrue(matchingRecord.isPresent());
        }
    }

    private NbsUserDetails validUserDetails() {
        return NbsUserDetails.builder()
                .authorities(addAndFindPatientAuthority())
                .build();
    }

    private NbsUserDetails invalidUserDetails() {
        return NbsUserDetails.builder()
                .authorities(addPatientAuthority())
                .build();
    }

    private Set<NbsAuthority> addAndFindPatientAuthority() {
        var authorities = new HashSet<NbsAuthority>();
        authorities.add(new NbsAuthority("ADD", "PATIENT", "", 123, "ALL", "ADD-PATIENT"));
        authorities.add(new NbsAuthority("FIND", "PATIENT", "", 124, "ALL", "FIND-PATIENT"));
        return authorities;
    }

    private Set<NbsAuthority> addPatientAuthority() {
        var authorities = new HashSet<NbsAuthority>();
        authorities.add(new NbsAuthority("ADD", "PATIENT", "", 123, "ALL", "ADD-PATIENT"));
        return authorities;
    }

    private PatientCreateRequest buildPatientCreateRequest() {
        var now = Instant.now();
        var patientInput = new PatientInput();
        patientInput.setNames(
                Arrays.asList(
                        new Name(
                                "First",
                                "Middle",
                                "Last",
                                Suffix.JR,
                                NameUseCd.L),
                        new Name(
                                "Second",
                                "SecondMiddle",
                                "SecondLast",
                                Suffix.SR,
                                NameUseCd.AL)));
        patientInput.setAddresses(Arrays
                .asList(new PostalAddress("SA1", "SA2", "City", "State", "County", "Country", "Zip",
                        "Census Tract")));
        patientInput.setBirthGender(Gender.M);
        patientInput.setCurrentGender(Gender.M);
        patientInput.setDateOfBirth(now.minus(25, ChronoUnit.DAYS));
        patientInput.setDeceased(Deceased.N);
        patientInput.setEmailAddresses(Arrays.asList("AnEmail@email.com"));
        patientInput.setEthnicityCode("EthCode");
        patientInput.setIdentifications(Arrays.asList(new Identification(
                "Id-Number", "Authority", "Type")));
        patientInput.setMaritalStatus("Marital Status");
        patientInput.setSsn("SSN");
        patientInput.setRaceCodes(Arrays.asList("Race Code1", "Race Code2"));
        patientInput.setPhoneNumbers(Arrays.asList(new PhoneNumber(
                "Phone Number", "Extension", PhoneType.CELL)));

        var request = new PatientCreateRequest();
        request.setRequestId("RequestId");
        request.setUserId("SomeUser");
        request.setPatientInput(patientInput);
        return request;
    }
}
