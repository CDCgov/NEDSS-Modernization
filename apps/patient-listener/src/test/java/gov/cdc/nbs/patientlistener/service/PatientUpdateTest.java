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


import gov.cdc.nbs.entity.enums.Ethnicity;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientInput.Name;
import gov.cdc.nbs.message.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.PatientInput.PhoneType;
import gov.cdc.nbs.message.PatientInput.PostalAddress;
import gov.cdc.nbs.message.PatientUpdateEventResponse;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patientlistener.util.Constants;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;

class PatientUpdateTest {

	@Mock
	PersonRepository personRepository;
	@Mock
	ElasticsearchPersonRepository elasticPersonRepository;

	@InjectMocks
	PatientService patientService;

	PatientUpdateTest() {
		MockitoAnnotations.openMocks(this);
		patientService = new PatientService(personRepository,elasticPersonRepository);
	}
	@Test
	void updatePatient() {

		String requestId = UUID.randomUUID().toString();
		Long id = UUID.randomUUID().getMostSignificantBits();

		Person person = buildPersonFromInput();

		person.setId(id);		
		when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
		PatientUpdateEventResponse result = patientService.updatePatient(requestId, id, getPatientInput());
		
		assertThat(result).isNotNull();
		assertThat(result.getRequestId()).isEqualTo(requestId);
		assertThat(result.getPersonId()).isEqualTo(id);
		assertThat(result.getStatus()).isEqualTo(Constants.COMPLETE);

	}

	@Test
	void updatedPersonBio() {
		Person result = patientService.updatedPersonBio(buildPersonFromInput(), getPatientInput());
		assertThat(result.getSsn()).isEqualTo(getPatientInput().getSsn());
		assertThat(result.getRaceCd()).isEqualTo(getPatientInput().getRaceCodes().get(0));
		assertThat(result.getBirthGenderCd()).isEqualTo(getPatientInput().getBirthGender());
		assertThat(result.getEthnicityGroupCd()).isEqualTo(getPatientInput().getEthnicityCode().substring(0, 20));

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

	private PatientInput getPatientInput() {
		PatientInput input = new PatientInput();

		Name pName = new Name();
		pName.setFirstName("JohnDoeFirstName");
		pName.setLastName("JohnDoeName");
		pName.setMiddleName("JDMiddleName");

		input.setNames(List.of(pName));

		input.setSsn("111-11-11111");
		Instant instant = Instant.parse("2023-01-03T10:15:30.00Z");
		input.setDateOfBirth(instant);

		input.setBirthGender(Gender.M);
		input.setCurrentGender(Gender.M);
		input.setDeceased(Deceased.FALSE);
		List<PostalAddress> addresses = new ArrayList<PostalAddress>();
		addresses.add(getAnAddress());
		input.setAddresses(addresses);
		List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
		numbers.add(phoneNumber());
		input.setPhoneNumbers(numbers);
		input.setEmailAddresses(List.of("test@test.com"));
		input.setEthnicityCode(Ethnicity.NOT_HISPANIC_OR_LATINO.toString());
		input.setRaceCodes(List.of("BK"));
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
		person.setEthnicGroupInd(Ethnicity.NOT_HISPANIC_OR_LATINO.toString());

		NBSEntity nbsEntity = new NBSEntity(identifier, "classCd");
		person.setNbsEntity(nbsEntity);
		nbsEntity.setId(UUID.randomUUID().getMostSignificantBits());
		nbsEntity.setClassCd("classIDTest");
		Participation part = new Participation();
		nbsEntity.setParticipations(List.of(part));
		return person;

	}

}
