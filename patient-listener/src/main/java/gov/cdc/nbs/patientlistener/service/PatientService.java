package gov.cdc.nbs.patientlistener.service;


import java.util.List;


//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.patientlistener.message.PatientUpdateEventResponse;
import gov.cdc.nbs.patientlistener.message.PatientUpdateParams;
import gov.cdc.nbs.patientlistener.odse.Person;
import gov.cdc.nbs.patientlistener.odse.PostalLocator;
import gov.cdc.nbs.patientlistener.odse.TeleLocator;
import gov.cdc.nbs.patientlistener.repository.PersonRepository;
import gov.cdc.nbs.patientlistener.repository.PostalLocatorRepository;
import gov.cdc.nbs.patientlistener.repository.TeleLocatorRepository;
import gov.cdc.nbs.patientlistener.util.Constants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

	//@PersistenceContext
	//private final EntityManager entityManager;
	private final PersonRepository personRepository;
	private final TeleLocatorRepository teleLocatorRepository;
	private final PostalLocatorRepository postalLocatorRepository;

	/**
	 * Update patientData received from Consumer Listener Event.
	 * 
	 * @param patientParams
	 * @return
	 */
	public PatientUpdateEventResponse updatePatient(PatientUpdateParams patientParams, String requestId) {

		if (requestId == null || requestId.length() < 1) {
			return PatientUpdateEventResponse.builder().personId(null).requestId(requestId).status(Constants.FAILED)
					.message(Constants.FAILED_NO_REQUESTID_MSG).build();

		}
		
		Person updated = patientParams.getUpdatePerson();
		try {
			
			List<PostalLocator> postalLocators = patientParams.getPostalLocators();
			List<TeleLocator> teleLocators = patientParams.getTeleLocators();

			teleLocatorRepository.saveAll(teleLocators);
			postalLocatorRepository.saveAll(postalLocators);
			personRepository.save(updated);
			
			PatientUpdateEventResponse event = PatientUpdateEventResponse.builder()
					.personId(updated.getId())
					.requestId(requestId)
			        .status(Constants.COMPLETE)
			        .build();

			return event;
		} catch (Exception e) {
			
			PatientUpdateEventResponse event = PatientUpdateEventResponse.builder()
					.personId(updated.getId())
					.requestId(requestId)
			        .status(Constants.FAILED)
			        .message(e.getMessage())
			        .build();
			
			return event;
		}

	}
	

}