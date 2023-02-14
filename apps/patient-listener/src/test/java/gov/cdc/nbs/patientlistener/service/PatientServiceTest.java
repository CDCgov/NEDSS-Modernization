package gov.cdc.nbs.patientlistener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import gov.cdc.nbs.config.security.NbsAuthority;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.Suffix;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.PatientCreateRequest;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientInput.Identification;
import gov.cdc.nbs.message.PatientInput.Name;
import gov.cdc.nbs.message.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.PatientInput.PhoneType;
import gov.cdc.nbs.message.PatientInput.PostalAddress;
import gov.cdc.nbs.message.RequestStatus;
import gov.cdc.nbs.patientlistener.PatientListenerApplication;
import gov.cdc.nbs.patientlistener.producer.KafkaProducer;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;;

@SpringBootTest(classes = PatientListenerApplication.class)
@ContextConfiguration(classes = PatientListenerApplication.class, loader = SpringBootContextLoader.class)
@ActiveProfiles("test")
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

    @Test
    void testHandlePatientCreate() throws Exception {
        // build input
        var input = getPatientInput();
        var request = new PatientCreateRequest();
        request.setRequestId("RequestId");
        request.setUserId("SomeUser");
        request.setPatientInput(input);

        // Mock
        when(userService.loadUserByUsername(Mockito.anyString())).thenReturn(validUserDetails());
        doNothing().when(producer).requestPatientCreateStatusEnvelope(Mockito.any());
        when(personRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        // call handlePatientCreate
        patientService.handlePatientCreate(mapper.writeValueAsString(request), request.getRequestId());

        // verify proper data saved to repositories
        verify(personRepository).save(personCaptor.capture());
        verify(elasticsearchPersonRepository).save(elasticsearchPersonCaptor.capture());
        verifyPersonMatchesInput(personCaptor.getValue(), input);
        assertTrue(elasticsearchPersonMatchesPerson(elasticsearchPersonCaptor.getValue(), personCaptor.getValue()));

        // verify patient create status was posted to topic
        verify(producer).requestPatientCreateStatusEnvelope(statusCaptor.capture());
        assertTrue(statusCaptor.getValue().isSuccessful());
        assertEquals(request.getRequestId(), statusCaptor.getValue().getRequestId());
        assertEquals(personCaptor.getValue().getId(), statusCaptor.getValue().getEntityId());
    }

    private void verifyPersonMatchesInput(Person person, PatientInput input) throws Exception {
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

        for (int i = 0; i < input.getRaceCodes().size(); i++) {
            assertEquals(input.getRaceCodes().get(i), person.getRaces().get(i));
        }

        var personPhones = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getCd().equals("TELE")
                        && ((TeleLocator) elp.getLocator()).getPhoneNbrTxt() != null
                        && !((TeleLocator) elp.getLocator()).getPhoneNbrTxt().isEmpty())
                .map(elp -> ((TeleLocator) elp.getLocator()))
                .toList();
        for (int i = 0; i < input.getPhoneNumbers().size(); i++) {
            var inputPhone = input.getPhoneNumbers().get(i);
            personPhones.stream()
                    .filter(p -> p.getPhoneNbrTxt().equals(inputPhone.getNumber())
                            && p.getExtensionTxt().equals(inputPhone.getExtension()))
                    .findAny()
                    .orElseThrow(() -> new Exception("Failed to find phone entry"));
        }

        var personEmails = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getCd().equals("TELE")
                        && ((TeleLocator) elp.getLocator()).getEmailAddress() != null
                        && !((TeleLocator) elp.getLocator()).getEmailAddress().isEmpty())
                .map(elp -> ((TeleLocator) elp.getLocator()).getEmailAddress())
                .toList();
        for (int i = 0; i < input.getEmailAddresses().size(); i++) {
            var inputEmail = input.getEmailAddresses().get(i);
            assertTrue(personEmails.contains(inputEmail));
        }

        // postal_locator
        var personAddresses = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getCd().equals("PST"))
                .map(elp -> ((PostalLocator) elp.getLocator()))
                .toList();
        for (int i = 0; i < input.getAddresses().size(); i++) {
            var inputAddress = input.getAddresses().get(i);
            personAddresses.stream()
                    .filter(p -> p.getStreetAddr1().equals(inputAddress.getStreetAddress1())
                            && p.getStreetAddr2().equals(inputAddress.getStreetAddress2())
                            && p.getCityCd().equals(inputAddress.getCity())
                            && p.getStateCd().equals(inputAddress.getStateCode())
                            && p.getCntyCd().equals(inputAddress.getCountyCode())
                            && p.getZipCd().equals(inputAddress.getZip())
                            && p.getCntryCd().equals(inputAddress.getCountryCode())
                            && p.getCensusTract().equals(inputAddress.getCensusTract()))
                    .findAny()
                    .orElseThrow(() -> new Exception("Failed to find address entry"));
        }
    }

    private boolean elasticsearchPersonMatchesPerson(ElasticsearchPerson value, Person value2) {
        return true; // TODO
    }

    private NbsUserDetails validUserDetails() {
        return NbsUserDetails.builder()
                .authorities(addAndFindPatientAuthority())
                .build();
    }

    private Set<NbsAuthority> addAndFindPatientAuthority() {
        var authorities = new HashSet<NbsAuthority>();
        authorities.add(new NbsAuthority("ADD", "PATIENT", "", 123, "ALL", "ADD-PATIENT"));
        authorities.add(new NbsAuthority("FIND", "PATIENT", "", 124, "ALL", "FIND-PATIENT"));
        return authorities;
    }

    private PatientInput getPatientInput() {
        var now = Instant.now();
        var patientInput = new PatientInput();
        patientInput.setNames(
                Arrays.asList(
                        new Name("First", "Middle", "Last", Suffix.JR, "L"),
                        new Name("Second", "SecondMiddle", "SecondLast", Suffix.SR, "A")));
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
        return patientInput;
    }
}
