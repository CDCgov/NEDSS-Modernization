package gov.cdc.nbs.patientlistener.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.PatientInput.PhoneType;
import gov.cdc.nbs.message.PatientUpdateEventResponse;
import gov.cdc.nbs.patientlistener.util.Constants;
import gov.cdc.nbs.patientlistener.util.PersonUtil;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
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

			return PatientUpdateEventResponse.builder().personId(personId)
					.requestId(requestId).status(Constants.COMPLETE).build();

		} catch (Exception e) {
			log.warn("Error while updating patientProfile: ", e);
			return PatientUpdateEventResponse.builder().personId(personId)
					.requestId(requestId).status(Constants.FAILED).message(e.getMessage()).build();
		}

	}

	public Person updatePatientProfile(Person oldPerson, Long iD, PatientInput input) {
		if (oldPerson != null && oldPerson.getId().equals(iD)) {

			oldPerson = updatedPersonName(oldPerson, input);

			oldPerson = updatedPersonBio(oldPerson, input);

			oldPerson = updatedPersonAddress(oldPerson, input);
			oldPerson = updatedPersonEmail(oldPerson, input);
			oldPerson = updatedPersonPhone(oldPerson, input);

		}
		return oldPerson;
	}
	
	public Person updatedPersonBio(Person oldPerson, PatientInput input) {

		oldPerson.setBirthGenderCd(
				input.getBirthGender() != null ? input.getBirthGender() : oldPerson.getBirthGenderCd());
		oldPerson.setCurrSexCd(input.getCurrentGender() != null ? input.getCurrentGender() : oldPerson.getCurrSexCd());
		oldPerson.setSsn(input.getSsn() != null ? input.getSsn() : oldPerson.getSsn());
		oldPerson.setRaceCd(getRaceCodes(input.getRaceCodes(),oldPerson));
		oldPerson.setDeceasedIndCd(input.getDeceased() != null ? input.getDeceased() : oldPerson.getDeceasedIndCd());
		oldPerson.setEthnicityGroupCd(
				input.getEthnicityCode() != null ? input.getEthnicityCode().substring(0, 20) : oldPerson.getEthnicityGroupCd());

		oldPerson.setBirthTime(input.getDateOfBirth() != null ? input.getDateOfBirth() : oldPerson.getBirthTime());

		return oldPerson;
	}
	
	public Person updatedPersonName(Person oldPerson, PatientInput input) {

		if (!input.getNames().isEmpty()) {
			oldPerson.setFirstNm(
					input.getNames().get(0).getFirstName() != null ? input.getNames().get(0).getFirstName() : oldPerson.getFirstNm());

			oldPerson.setMiddleNm(input.getNames().get(0).getMiddleName() != null ? input.getNames().get(0).getMiddleName()
					: oldPerson.getMiddleNm());

			oldPerson.setLastNm(
					input.getNames().get(0).getLastName() != null ? input.getNames().get(0).getLastName() : oldPerson.getLastNm());

			oldPerson.setNmSuffix(
					input.getNames().get(0).getSuffix() != null ? input.getNames().get(0).getSuffix() : oldPerson.getNmSuffix());
		}

		return oldPerson;
	}

	public Person updatedPersonAddress(Person oldPerson, PatientInput input) {

		if (input.getAddresses() != null && !input.getAddresses().isEmpty()) {
			oldPerson.setHmCntyCd(input.getAddresses().get(0).getCountyCode());
			oldPerson.setHmCntryCd(input.getAddresses().get(0).getCountryCode());
			oldPerson.setHmStateCd(input.getAddresses().get(0).getStateCode());
			oldPerson.setHmCityCd(input.getAddresses().get(0).getCity());
			oldPerson.setHmZipCd(input.getAddresses().get(0).getZip());
		}
		return oldPerson;
	}

	public Person updatedPersonEmail(Person oldPerson, PatientInput input) {

		if (input.getEmailAddresses() != null && !input.getEmailAddresses().isEmpty()) {
			oldPerson.setHmEmailAddr(input.getEmailAddresses().get(0));
			oldPerson.setWkEmailAddr(input.getEmailAddresses().size() >= 2 && input.getEmailAddresses().get(1) != null
					? input.getEmailAddresses().get(1)
					: oldPerson.getWkEmailAddr());
		}

		return oldPerson;
	}

	public Person updatedPersonPhone(Person oldPerson, PatientInput input) {

		for (PhoneNumber number : input.getPhoneNumbers()) {
			if (number.getPhoneType().equals(PhoneType.HOME)) {
				oldPerson.setHmPhoneNbr(number.getNumber());
			}
			if (number.getPhoneType().equals(PhoneType.WORK)) {
				oldPerson.setWkPhoneNbr(number.getNumber());
			}
			if (number.getPhoneType().equals(PhoneType.CELL)) {
				oldPerson.setCellPhoneNbr(number.getNumber());
			}

		}
		return oldPerson;
	}
	
	private String getRaceCodes(List<String> raceCodes, Person oldPerson) {
		StringBuilder race = new StringBuilder();
		if (raceCodes == null || raceCodes.isEmpty()) {
			return oldPerson.getRaceCd();
		}

		for (String raceC : raceCodes) {
			race.append(raceC);
		}

		return race.toString().length() > 20 ? race.toString().substring(0, 20) :  race.toString();

	}

}