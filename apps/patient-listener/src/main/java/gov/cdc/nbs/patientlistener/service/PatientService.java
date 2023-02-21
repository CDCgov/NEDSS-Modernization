package gov.cdc.nbs.patientlistener.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import gov.cdc.nbs.patientlistener.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.patientlistener.message.PatientUpdateEventResponse;
import gov.cdc.nbs.patientlistener.odse.PatientInput;
import gov.cdc.nbs.patientlistener.odse.PatientInput.PhoneNumber;
import gov.cdc.nbs.patientlistener.odse.PatientInput.PhoneType;
import gov.cdc.nbs.patientlistener.odse.Person;
import gov.cdc.nbs.patientlistener.repository.PersonRepository;
import gov.cdc.nbs.patientlistener.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.patientlistener.util.Constants;
import gov.cdc.nbs.patientlistener.util.PersonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {
	private final PersonRepository personRepository;
	private final ElasticsearchPersonRepository elasticPersonRepository;

	/**
	 * Update patientData received from Consumer Listener Event.
	 * 
	 * @param patientParams
	 * @return
	 */
	public PatientUpdateEventResponse updatePatient(String requestId, Long personId, PatientInput input) {

		Person updated = null;
		if (requestId == null || requestId.length() < 1) {
			return PatientUpdateEventResponse.builder().personId(null).requestId(requestId).status(Constants.FAILED)
					.message(Constants.FAILED_NO_REQUESTID_MSG).build();

		}

		Optional<Person> dbPerson = personRepository.findById(personId);
		if (dbPerson.isEmpty()) {
			return PatientUpdateEventResponse.builder().personId(null).requestId(requestId).status(Constants.FAILED)
					.message(Constants.FAILED_PERSON_NOT_FIND_MSG + personId).build();
		}

		updated = updatePatientProfile(dbPerson.get(), personId, input);
		try {

			personRepository.save(updated);
			ElasticsearchPerson elasticUpdate = PersonUtil.getElasticSearchPerson(updated);
			elasticPersonRepository.save(elasticUpdate);

			PatientUpdateEventResponse event = PatientUpdateEventResponse.builder().personId(personId)
					.requestId(requestId).status(Constants.COMPLETE).build();

			return event;
		} catch (Exception e) {
			log.warn("Error while updating patientProfile: ", e);
			PatientUpdateEventResponse event = PatientUpdateEventResponse.builder().personId(personId)
					.requestId(requestId).status(Constants.FAILED).message(e.getMessage()).build();

			return event;
		}

	}

	public Person updatePatientProfile(Person oldPerson, Long Id, PatientInput input) {
		if (oldPerson != null && oldPerson.getId().equals(Id) ){

			if (input.getName() != null) {
				oldPerson.setFirstNm(input.getName().getFirstName() != null ? input.getName().getFirstName()
						: oldPerson.getFirstNm());

				oldPerson.setMiddleNm(input.getName().getMiddleName() != null ? input.getName().getMiddleName()
						: oldPerson.getMiddleNm());

				oldPerson.setLastNm(
						input.getName().getLastName() != null ? input.getName().getLastName() : oldPerson.getLastNm());

				oldPerson.setNmSuffix(
						input.getName().getSuffix() != null ? input.getName().getSuffix() : oldPerson.getNmSuffix());
			}

			oldPerson.setBirthGenderCd(
					input.getBirthGender() != null ? input.getBirthGender() : oldPerson.getBirthGenderCd());
			oldPerson.setCurrSexCd(
					input.getCurrentGender() != null ? input.getCurrentGender() : oldPerson.getCurrSexCd());
			oldPerson.setSsn(input.getSsn() != null ? input.getSsn() : oldPerson.getSsn());
			oldPerson.setRaceCd(input.getRace() != null ? input.getRace() : oldPerson.getRaceCd());
			oldPerson
					.setDeceasedIndCd(input.getDeceased() != null ? input.getDeceased() : oldPerson.getDeceasedIndCd());
			oldPerson.setEthnicityGroupCd(input.getEthnicity() != null ? input.getEthnicity().substring(0, 20)
					: oldPerson.getEthnicityGroupCd());

			oldPerson.setBirthTime(input.getDateOfBirth() != null ? input.getDateOfBirth() : oldPerson.getBirthTime());

			if (input.getAddresses() != null && input.getAddresses().size() >= 1) {
				oldPerson.setHmCntyCd(input.getAddresses().get(0).getCountyCode());
				oldPerson.setHmCntryCd(input.getAddresses().get(0).getCountryCode());
				oldPerson.setHmStateCd(input.getAddresses().get(0).getStateCode());
				oldPerson.setHmCityCd(input.getAddresses().get(0).getCity());
				oldPerson.setHmZipCd(input.getAddresses().get(0).getZip());
			}

			if (input.getEmailAddresses() != null && input.getEmailAddresses().size() >= 1) {
				oldPerson.setHmEmailAddr(input.getEmailAddresses().get(0));
				oldPerson.setWkEmailAddr(
						input.getEmailAddresses().size() >= 2 && input.getEmailAddresses().get(1) != null
								? input.getEmailAddresses().get(1)
								: oldPerson.getWkEmailAddr());
			}

			for (PhoneNumber number : input.getPhoneNumbers()) {
				if (number.getPhoneType().equals(PhoneType.HOME)) {
					oldPerson.setHmPhoneNbr(number.getNumber());
				}
				if (number.getPhoneType().equals(PhoneType.WORK)) {
					oldPerson.setWkPhoneNbr(number.getNumber());
				}
				if (number.getPhoneType().equals(PhoneType.WORK)) {
					oldPerson.setCellPhoneNbr(number.getNumber());
				}

			}

		}
		return oldPerson;
	}

}