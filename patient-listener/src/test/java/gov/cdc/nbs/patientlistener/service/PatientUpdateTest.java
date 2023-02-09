package gov.cdc.nbs.patientlistener.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gov.cdc.nbs.patientlistener.PatientListenerApplication;
import gov.cdc.nbs.patientlistener.enums.Deceased;
import gov.cdc.nbs.patientlistener.enums.Ethnicity;
import gov.cdc.nbs.patientlistener.enums.Gender;
import gov.cdc.nbs.patientlistener.message.PatientUpdateEventResponse;
import gov.cdc.nbs.patientlistener.message.PatientUpdateParams;
import gov.cdc.nbs.patientlistener.odse.EntityLocatorParticipation;
import gov.cdc.nbs.patientlistener.odse.NBSEntity;
import gov.cdc.nbs.patientlistener.odse.Participation;
import gov.cdc.nbs.patientlistener.odse.Person;
import gov.cdc.nbs.patientlistener.odse.PostalLocator;
import gov.cdc.nbs.patientlistener.odse.TeleLocator;
import gov.cdc.nbs.patientlistener.repository.PersonRepository;
import gov.cdc.nbs.patientlistener.repository.PostalLocatorRepository;
import gov.cdc.nbs.patientlistener.repository.TeleLocatorRepository;
import gov.cdc.nbs.patientlistener.util.Constants;

@SpringBootTest(classes = PatientListenerApplication.class, properties = { "spring.profiles.active:test" })
@RunWith(SpringRunner.class)
class PatientUpdateTest {
	@Mock
	PersonRepository personRepository;
	@Mock
	TeleLocatorRepository teleLocatorRepository;
	@Mock
	PostalLocatorRepository postalLocatorRepository;

	@InjectMocks
	PatientService patientService;
	
	public PatientUpdateTest() {
		MockitoAnnotations.openMocks(this);
		//patientService = new PatientService(personRepository, teleLocatorRepository, postalLocatorRepository);

		patientService = new PatientService(personRepository, teleLocatorRepository, postalLocatorRepository);

		
		when(personRepository.save(Mockito.any())).thenReturn(buildPersonFromInput());
		when(teleLocatorRepository.save(Mockito.any())).thenReturn(List.of(teleLocator()));
		when(postalLocatorRepository.save(Mockito.any())).thenReturn(postalLocator());
	}

	@Test
	void updatedPatientRequestIdNotProvided() {
		PatientUpdateEventResponse updatedPersonResponse = patientService.updatePatient(getPatientParams(), null);
		assertNotNull(updatedPersonResponse);
		assertEquals(updatedPersonResponse.getStatus(), Constants.FAILED);
		assertEquals(updatedPersonResponse.getMessage(), Constants.FAILED_NO_REQUESTID_MSG);

	}

	@Test
	void updatePatient() {
		String requestId = getRequestID();
		PatientUpdateEventResponse updatedPersonResponse = patientService.updatePatient(getPatientParams(), requestId);
		assertNotNull(updatedPersonResponse);
		assertEquals(updatedPersonResponse.getStatus(), Constants.COMPLETE);
		assertEquals(requestId, updatedPersonResponse.getRequestId());

	}

	private String getRequestID() {
		return String.format("UnitTest" + "_%s", UUID.randomUUID());
	}

	private PatientUpdateParams getPatientParams() {
		return PatientUpdateParams.builder().updatePerson(buildPersonFromInput())
				.postalLocators(List.of(postalLocator())).teleLocators(List.of(teleLocator())).build();

	}

	private PostalLocator postalLocator() {
		PostalLocator postal = new PostalLocator();
		return postal;
	}

	private TeleLocator teleLocator() {
		TeleLocator locater = new TeleLocator();
		return locater;
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
