package gov.cdc.nbs.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gov.cdc.nbs.Application;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.repository.ObservationRepository;
import gov.cdc.nbs.repository.ParticipationRepository;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.service.EventService;
import gov.cdc.nbs.util.Constants;

@SpringBootTest(classes = Application.class, properties = { "spring.profiles.active:test" })
@RunWith(SpringRunner.class)
 class FindMorbidityReportsForPatientTest {

	@Mock
	PersonRepository personRepository;

	@Mock
	ParticipationRepository participationRepository;

	@Mock
	ObservationRepository oboservationRepository;

	@InjectMocks
	EventService eventService;

	public FindMorbidityReportsForPatientTest() {
		MockitoAnnotations.openMocks(this);
		eventService = new EventService(null, null, null);
	}

	@Test
	void FindMorbidityReportsTest() {
		Long patientID = 100543L;
		BigInteger personsIDs = BigInteger.valueOf(patientID).add(BigInteger.ONE);
		BigInteger actIDs = personsIDs.add(BigInteger.ONE);
		Long actIdParam = patientID + 2;
		Long resultID = patientID + 3;
		Object[] subjectIds = new Object[] { personsIDs };
		List<Object[]> subjectIdsSet = new ArrayList<Object[]>();
		subjectIdsSet.add(subjectIds);

		Object[] actIds = new Object[] { actIDs };
		List<Object[]> actIdsSet = new ArrayList<Object[]>();
		actIdsSet.add(actIds);

		when(personRepository.getPersonIdsByPersonParentId(Mockito.anyLong())).thenReturn(subjectIdsSet);
		when(participationRepository.findIdActUidByIdTypeCdAndIdSubjectEntityUidIn(Constants.REPORT_TYPE,
				List.of(personsIDs))).thenReturn(actIdsSet);
		Observation obs = new Observation();
		obs.setId(patientID + 3);
		when(oboservationRepository.findByIdIn(List.of(actIdParam))).thenReturn(List.of(obs));

		List<Observation> observations = eventService.findMorbidityReportsForPatient(patientID);
		assertNotNull(observations);
		assertTrue(observations.size() > 0);
		assertEquals(observations.get(0).getId(), resultID);
	}

}
