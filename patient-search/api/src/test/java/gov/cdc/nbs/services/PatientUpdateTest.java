package gov.cdc.nbs.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

import java.time.Instant;

import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import gov.cdc.nbs.Application;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Ethnicity;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.Race;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.input.PatientInput;
import gov.cdc.nbs.graphql.input.PatientInput.Name;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneNumber;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneType;
import gov.cdc.nbs.graphql.input.PatientInput.PostalAddress;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import gov.cdc.nbs.service.PatientService;

@SpringBootTest(classes = Application.class, properties = { "spring.profiles.active:test" })
@RunWith(SpringRunner.class)
class PatientUpdateTest {
	@Mock
	PersonRepository personRepository;
	@Mock
	TeleLocatorRepository teleLocatorRepository;
	@Mock
	PostalLocatorRepository postalLocatorRepository;

	@Mock
	SecurityContextHolder securitContextHolder;

	@InjectMocks
	PatientService patientService;

	Person person;
	Long ID;

	public PatientUpdateTest() {
		MockitoAnnotations.openMocks(this);
		patientService = new PatientService(null, personRepository, teleLocatorRepository, postalLocatorRepository,
				null, null);
		Long id = UUID.randomUUID().getMostSignificantBits();
		Person old = buildPersonFromInput();
		person = old;
		ID = id;

		when(personRepository.getById(Mockito.anyLong())).thenReturn(old);
		when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(old));

		Authentication auth = Mockito.mock(Authentication.class);
		SecurityContext secCont = Mockito.mock(SecurityContext.class);
		securitContextHolder.setContext(secCont);

		when(teleLocatorRepository.getMaxId()).thenReturn(ID + 1);
		when(securitContextHolder.getContext().getAuthentication()).thenReturn(auth);

		NbsUserDetails principal = new NbsUserDetails(id, "testUserName", "testFirstName", null, false, false, null,
				null, null, null, false);

		Mockito.when(auth.getPrincipal()).thenReturn(principal);
	}

	@Test
	void updatedPatientNotFound() {
		Person updatedPerson = patientService.updatePatient(ID, null);
		assertNull(updatedPerson);

	}

	@Test
	void updatePatient() {
		Person updatedPerson = patientService.updatePatient(ID, getPatientInput());

		assertEquals(person.getBirthGenderCd(), updatedPerson.getBirthGenderCd());
		assertEquals(person.getBirthTime(), updatedPerson.getBirthTime());
		assertEquals(person.getCurrSexCd(), updatedPerson.getCurrSexCd());
		assertEquals(person.getBirthTime(), updatedPerson.getBirthTime());
		assertEquals(person.getFirstNm(), updatedPerson.getFirstNm());
		assertEquals(person.getMiddleNm(), updatedPerson.getMiddleNm());
		assertEquals(person.getLastNm(), updatedPerson.getLastNm());
		assertEquals(person.getNames(), updatedPerson.getNames());
		assertEquals(person.getNbsEntity() , updatedPerson.getNbsEntity());	
		assertEquals(person.getRaces(), updatedPerson.getRaces());
		assertEquals(person.getRaces(), updatedPerson.getRaces());
		assertEquals(person.getDeceasedIndCd(), updatedPerson.getDeceasedIndCd());
		assertEquals(person.getEthnicGroupInd(), updatedPerson.getEthnicGroupInd());
		assertEquals(person.getSsn(), updatedPerson.getSsn());

	}

	private PatientInput getPatientInput() {
		PatientInput input = new PatientInput();

		Name pName = new Name();
		pName.setFirstName("UnitTestFirstName");
		pName.setLastName("UnitTestLastName");
		pName.setMiddleName("UnitTestMiddleName");

		input.setName(pName);

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
		input.setEthnicity(Ethnicity.NOT_HISPANIC_OR_LATINO);
		input.setRace(Race.AFRICAN_AMERICAN);
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
		Person person = new Person();

		person.setFirstNm("GenericFirstName");
		person.setLastNm("GenericFirstLastName");
		person.setMiddleNm("GenericMiddleName");

		person.setSsn("222-22-2222");
		person.setBirthTime(Instant.now());

		person.setBirthGenderCd(Gender.F);
		person.setCurrSexCd(Gender.F);
		person.setDeceasedIndCd(Deceased.FALSE);
		person.setEthnicGroupInd(Ethnicity.NOT_HISPANIC_OR_LATINO);

		NBSEntity nbsEntity = new NBSEntity();
		person.setNbsEntity(nbsEntity);
		nbsEntity.setId(UUID.randomUUID().getMostSignificantBits());
		nbsEntity.setClassCd("classIDTest");
		EntityLocatorParticipation entityLocPart = new EntityLocatorParticipation();
		nbsEntity.setEntityLocatorParticipations(List.of(entityLocPart));
		Participation part = new Participation();
		nbsEntity.setParticipations(List.of(part));
		return person;

	}

}
