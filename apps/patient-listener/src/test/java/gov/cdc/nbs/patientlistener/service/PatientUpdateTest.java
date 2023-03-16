package gov.cdc.nbs.patientlistener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientInput.Name;
import gov.cdc.nbs.message.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.PatientInput.PhoneType;
import gov.cdc.nbs.message.PatientInput.PostalAddress;
import gov.cdc.nbs.message.PatientUpdateEventResponse;
import gov.cdc.nbs.message.TemplateInput;
import gov.cdc.nbs.message.UpdateMortality;
import gov.cdc.nbs.message.UpdateSexAndBirth;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.util.Constants;
import gov.cdc.nbs.repository.EntityLocatorParticipationRepository;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;

class PatientUpdateTest {

    @Mock
    PersonRepository personRepository;
    @Mock
    ElasticsearchPersonRepository elasticPersonRepository;

    @Mock
    PostalLocatorRepository postalLocatorRepository;

    @Mock
    EntityLocatorParticipationRepository entityLocatorPartRepository;

    @InjectMocks
    PatientService patientService;

    PatientUpdateTest() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(personRepository, elasticPersonRepository, postalLocatorRepository,
                entityLocatorPartRepository);
    }

    @Test
    void updatePatient() {

        List<TemplateInput> vars = new ArrayList<TemplateInput>();
        String requestId = UUID.randomUUID().toString();
        Long id = UUID.randomUUID().getMostSignificantBits();

        Person person = buildPersonFromInput();

        TemplateInput update = new TemplateInput();
        update.setKey("updateType");
        update.setValue(Constants.UPDATE_GENERAL_INFO);
        vars.add(update);

        person.setId(id);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        PatientUpdateEventResponse result = patientService.updatePatient(requestId, id, getPatientInput(),
                getPatientMortality(), getPatientSexAndBirth(), vars);

        assertThat(result).isNotNull();
        assertThat(result.getRequestId()).isEqualTo(requestId);
        assertThat(result.getPersonId()).isEqualTo(id);
        assertThat(result.getStatus()).isEqualTo(Constants.COMPLETE);

    }


    @Test
    void updatedPersonName() {
        Person result = patientService.updatedPersonName(buildPersonFromInput(), getPatientInput());
        assertThat(result.getFirstNm()).isEqualTo(getPatientInput().getNames().get(0).getFirstName());
        assertThat(result.getLastNm()).isEqualTo(getPatientInput().getNames().get(0).getLastName());
        assertThat(result.getMiddleNm()).isEqualTo(getPatientInput().getNames().get(0).getMiddleName());
    }

    @Test
    void updatedPersonAddress() {

        Person result = patientService.updatedPersonAddress(buildPersonFromInput(), getPatientInput());
        assertThat(result.getHmCntryCd()).isEqualTo(getPatientInput().getAddresses().get(0).getCountryCode());
        assertThat(result.getHmCntyCd()).isEqualTo(getPatientInput().getAddresses().get(0).getCountyCode());
        assertThat(result.getHmStateCd()).isEqualTo(getPatientInput().getAddresses().get(0).getStateCode());
        assertThat(result.getHmCityCd()).isEqualTo(getPatientInput().getAddresses().get(0).getCity());
        assertThat(result.getHmZipCd()).isEqualTo(getPatientInput().getAddresses().get(0).getZip());
    }

    @Test
    void updatedPersonEmail() {
        Person result = patientService.updatedPersonEmail(buildPersonFromInput(), getPatientInput());
        assertThat(result.getHmEmailAddr()).isEqualTo(getPatientInput().getEmailAddresses().get(0));

    }

    @Test
    void updatedPersonPhone() {
        Person result = patientService.updatedPersonPhone(buildPersonFromInput(), getPatientInput());
        assertThat(result.getCellPhoneNbr()).isEqualTo(phoneNumber().getNumber());

    }

    @Test
    void updatedGeneralInfo() {
        Person result = patientService.updatedGeneralInfo(buildPersonFromInput(), getPatientInput());
        assertThat(result.getSpeaksEnglishCd()).isEqualTo("Y");
        assertThat(result.getPrimLangCd()).isEqualTo("Spanish");

    }

    @Test
    void updatedSexAndBirth() {
        Person result = patientService.updatedSexAndBirth(buildPersonFromInput(), getPatientSexAndBirth());
        assertThat(result.getBirthGenderCd()).isEqualTo(Gender.M);
        assertThat(result.getCurrSexCd()).isEqualTo(Gender.M);
    }

    @Test
    void updatedMortality() {
        Person result = patientService.updatedMortality(buildPersonFromInput(), getPatientMortality());
        assertThat(result.getDeceasedIndCd()).isEqualTo(Deceased.Y);

    }


    private PatientInput getPatientInput() {
        PatientInput input = new PatientInput();

        Name pName = new Name();
        pName.setFirstName("JohnDoeFirstName");
        pName.setLastName("JohnDoeName");
        pName.setMiddleName("JDMiddleName");

        input.setNames(List.of(pName));

        input.setSsn("111-11-11111");
        List<PostalAddress> addresses = new ArrayList<PostalAddress>();
        addresses.add(getAnAddress());
        input.setAddresses(addresses);
        List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
        numbers.add(phoneNumber());
        input.setPhoneNumbers(numbers);
        input.setEmailAddresses(List.of("test@test.com"));
        input.setEthnicityCode("2186-5");
        input.setRaceCodes(List.of("BK"));
        input.setSpeaksEnglish("Y");
        input.setPrimaryLang("Spanish");

        return input;

    }

    private UpdateMortality getPatientMortality() {
        UpdateMortality input = new UpdateMortality(Instant.now(), Deceased.Y, Instant.now(), "City", "State", "County",
                "Country");
        return input;
    }

    private UpdateSexAndBirth getPatientSexAndBirth() {
        short order = 1;
        UpdateSexAndBirth input = new UpdateSexAndBirth(Instant.now(), Instant.now(), Gender.M, Gender.M, Gender.F,
                Gender.U, "birthCity", "birthCntry", "birthState", order, "Y", "U", "27", Instant.now());
        return input;
    }

    private PostalAddress getAnAddress() {
        PostalAddress address = new PostalAddress();
        address.setStreetAddress1("123 fake street");
        address.setCountryCode("01");
        address.setCountyCode("12345");
        address.setStateCode("13");
        address.setZip("33333");

        return address;

    }

    private PhoneNumber phoneNumber() {
        PhoneNumber number = new PhoneNumber();

        number.setNumber("555-555-5555");
        number.setExtension("001");
        number.setPhoneType(PhoneType.CELL);

        return number;
    }

    private Person buildPersonFromInput() {
        long identifier = 1234L;
        Person person = new Person(identifier, "localId");

        person.setFirstNm("JohnDoeFirstName");
        person.setLastNm("JohnDoeLastName");
        person.setMiddleNm("JohnDoeMName");

        person.setSsn("222-22-2222");
        person.setBirthTime(Instant.now());

        person.setBirthGenderCd(Gender.F);
        person.setCurrSexCd(Gender.F);
        person.setDeceasedIndCd(Deceased.FALSE);
        person.setEthnicGroupInd("2186-5");

        NBSEntity nbsEntity = new NBSEntity(identifier, "classCd");
        person.setNbsEntity(nbsEntity);
        nbsEntity.setId(UUID.randomUUID().getMostSignificantBits());
        nbsEntity.setClassCd("classIDTest");
        Participation part = new Participation();
        nbsEntity.setParticipations(List.of(part));
        return person;

    }

}
