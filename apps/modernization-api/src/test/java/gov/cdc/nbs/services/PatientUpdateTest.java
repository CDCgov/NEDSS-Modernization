package gov.cdc.nbs.services;

import gov.cdc.nbs.Application;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.service.PatientService;
import gov.cdc.nbs.support.EthnicityMother;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class, properties = { "spring.profiles.active:test" })
@RunWith(SpringRunner.class)
class PatientUpdateTest {
	@Mock
	PersonRepository personRepository;
	@Mock
	SecurityContextHolder securitContextHolder;

	@InjectMocks
	PatientService patientService;

	Person person;
	Long ID;

	@SuppressWarnings("deprecation")
	public PatientUpdateTest() {
		MockitoAnnotations.openMocks(this);
		patientService = new PatientService(null, personRepository, null, null,
				null, null);
		Long id = UUID.randomUUID().getMostSignificantBits();
		Person old = buildPersonFromInput();
		person = old;
		ID = id;

		when(personRepository.getById(Mockito.anyLong())).thenReturn(old);
		when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(old));

		Authentication auth = Mockito.mock(Authentication.class);
		SecurityContext secCont = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(secCont);

		when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);

		NbsUserDetails principal = new NbsUserDetails(id, "testUserName", "testFirstName", null, false, false, null,
				null, null, null, false);

		Mockito.when(auth.getPrincipal()).thenReturn(principal);
	}

	private Person buildPersonFromInput() {
		Person person = new Person(6037L, "PSN2467");

		person.setFirstNm("GenericFirstName");
		person.setLastNm("GenericFirstLastName");
		person.setMiddleNm("GenericMiddleName");

		person.setSsn("222-22-2222");
		person.setBirthTime(Instant.now());

		person.setBirthGenderCd(Gender.F);
		person.setCurrSexCd(Gender.F);
		person.setDeceasedIndCd(Deceased.FALSE);
		person.setEthnicGroupInd(EthnicityMother.NOT_HISPANIC_OR_LATINO_CODE);

		NBSEntity nbsEntity = new NBSEntity(UUID.randomUUID().getMostSignificantBits(), "classIDTest");
		person.setNbsEntity(nbsEntity);

		Participation part = new Participation();
		nbsEntity.setParticipations(List.of(part));
		return person;

	}

}
