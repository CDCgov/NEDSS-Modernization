package gov.cdc.nbs.patientlistener.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.PatientInput.PhoneType;
import gov.cdc.nbs.message.PatientUpdateEventResponse;
import gov.cdc.nbs.message.TemplateInput;
import gov.cdc.nbs.message.UpdateMortality;
import gov.cdc.nbs.message.UpdateSexAndBirth;
import gov.cdc.nbs.message.util.Constants;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patientlistener.util.PersonUtil;
import gov.cdc.nbs.repository.EntityLocatorParticipationRepository;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {
    private final PersonRepository personRepository;
    private final ElasticsearchPersonRepository elasticPersonRepository;
    private final PostalLocatorRepository postalLocatorRepository;
    private final EntityLocatorParticipationRepository entityLocatorPartRepository;

    /**
     * Update patientData received from Consumer Listener Event.
     * 
     * @param patientParams
     * @return
     */
    public PatientUpdateEventResponse updatePatient(String requestId, Long personId, PatientInput input,
            UpdateMortality mortalityInput, UpdateSexAndBirth inputSexAndBirth, List<TemplateInput> vars) {

        Person updated = null;
        if (requestId == null || requestId.length() < 1) {
            return PatientUpdateEventResponse.builder().personId(personId).requestId(requestId).status(Constants.FAILED)
                    .message(Constants.FAILED_NO_REQUESTID_MSG).build();

        }

        Optional<Person> dbPerson = personRepository.findById(personId);
        if (dbPerson.isEmpty()) {
            return PatientUpdateEventResponse.builder().personId(personId).requestId(requestId).status(Constants.FAILED)
                    .message(Constants.FAILED_PERSON_NOT_FIND_MSG + personId).build();
        }

        TemplateInput updateInput = vars.get(0);
        String updateType = updateInput.getValue();
        updated = updatePatient(updateType, dbPerson.get(), input, mortalityInput, inputSexAndBirth);


        try {

            if (updated != null) {
                personRepository.save(updated);
                ElasticsearchPerson elasticUpdate = PersonUtil.getElasticSearchPerson(updated);
                elasticPersonRepository.save(elasticUpdate);
            }

            return PatientUpdateEventResponse.builder().personId(personId)
                    .requestId(requestId).status(Constants.COMPLETE).build();

        } catch (Exception e) {
            log.warn("Error while updating patientProfile: ", e);
            return PatientUpdateEventResponse.builder().personId(personId)
                    .requestId(requestId).status(Constants.FAILED).message(e.getMessage()).build();
        }

    }

    private Person updatePatient(String updateType, Person person, PatientInput input, UpdateMortality mortalityInput,
            UpdateSexAndBirth inputSexAndBirth) {
        Person updated = null;
        if (updateType.equals(Constants.UPDATE_GENERAL_INFO)) {

            updated = updatedGeneralInfo(person, input);
        }
        if (updateType.equals(Constants.UPDATE_SEX_BIRTH)) {

            updated = updatedSexAndBirth(person, inputSexAndBirth);

        }
        if (updateType.equals(Constants.UPDATE_MORTALITY)) {
            PostalLocator deathRecord = null;
            Long locatorId = entityLocatorPartRepository
                    .getLocatorIdByPersonParentId(person.getId());

            if (locatorId != null && locatorId > 0) {
                Optional<PostalLocator> findPostalRecord = postalLocatorRepository.findById(locatorId);
                if (findPostalRecord.isPresent()) {

                    deathRecord = findPostalRecord.get();
                }
            } else {
                Long maxId = postalLocatorRepository.getMaxId();
                deathRecord = new PostalLocator();
                deathRecord.setId(maxId + 1);

            }
            setDeathRecordInfo(deathRecord, mortalityInput);


            PostalLocator savedRecord = postalLocatorRepository.save(deathRecord);
            PostalEntityLocatorParticipation postalEntityRecord = setEntityLocatorParticipation(savedRecord, person,
                    mortalityInput);
            entityLocatorPartRepository.save(postalEntityRecord);
            updated = updatedMortality(person, mortalityInput);

        }

        return updated;
    }

    private PostalLocator setDeathRecordInfo(PostalLocator deathRecord, UpdateMortality input) {

        deathRecord.setCityDescTxt(input.cityOfDeath() != null ? input.cityOfDeath() : null);
        deathRecord.setStateCd(input.stateOfDeath() != null ? input.stateOfDeath() : null);
        deathRecord.setCntyCd(input.countyOfDeath() != null ? input.countyOfDeath() : null);
        deathRecord.setCntryCd(input.countryOfDeath() != null ? input.countryOfDeath() : null);

        return deathRecord;
    }


    public Person updatedPersonBio(Person oldPerson, PatientInput input) {

        oldPerson.setSsn(input.getSsn() != null ? input.getSsn() : oldPerson.getSsn());
        oldPerson.setRaceCd(getRaceCodes(input.getRaceCodes(), oldPerson));
        oldPerson.setEthnicityGroupCd(
                input.getEthnicityCode() != null ? input.getEthnicityCode().substring(0, 20)
                        : oldPerson.getEthnicityGroupCd());
        return oldPerson;
    }

    public Person updatedPersonName(Person oldPerson, PatientInput input) {

        if (!input.getNames().isEmpty()) {
            oldPerson.setFirstNm(
                    input.getNames().get(0).getFirstName() != null ? input.getNames().get(0).getFirstName()
                            : oldPerson.getFirstNm());

            oldPerson.setMiddleNm(
                    input.getNames().get(0).getMiddleName() != null ? input.getNames().get(0).getMiddleName()
                            : oldPerson.getMiddleNm());

            oldPerson.setLastNm(
                    input.getNames().get(0).getLastName() != null ? input.getNames().get(0).getLastName()
                            : oldPerson.getLastNm());

            oldPerson.setNmSuffix(
                    input.getNames().get(0).getSuffix() != null ? input.getNames().get(0).getSuffix()
                            : oldPerson.getNmSuffix());
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

    public Person updatedGeneralInfo(Person oldPerson, PatientInput input) {

        oldPerson.setAsOfDateGeneral(input.getAsOf() != null ? input.getAsOf() : oldPerson.getAsOfDateGeneral());
        oldPerson.setMaritalStatusCd(
                input.getMaritalStatus() != null ? input.getMaritalStatus() : oldPerson.getMaritalStatusCd());
        oldPerson.setMothersMaidenNm(
                input.getMothersMaidenName() != null ? input.getMothersMaidenName() : oldPerson.getMothersMaidenNm());
        oldPerson.setAdultsInHouseNbr(
                input.getAdultNbrInHouse() != null ? input.getAdultNbrInHouse() : oldPerson.getAdultsInHouseNbr());
        oldPerson.setChildrenInHouseNbr(input.getChildrenNbrinHouse() != null ? input.getChildrenNbrinHouse()
                : oldPerson.getChildrenInHouseNbr());
        oldPerson.setOccupationCd(
                input.getPrimaryOccupation() != null ? input.getPrimaryOccupation() : oldPerson.getOccupationCd());
        oldPerson.setEducationLevelCd(input.getHighestEducationLvl() != null ? input.getHighestEducationLvl()
                : oldPerson.getEducationLevelCd());
        oldPerson.setPrimLangCd(input.getPrimaryLang() != null ? input.getPrimaryLang() : oldPerson.getPrimLangCd());
        oldPerson.setSpeaksEnglishCd(
                input.getSpeaksEnglish() != null ? input.getSpeaksEnglish() : oldPerson.getSpeaksEnglishCd());
        oldPerson.setEharsId(input.getHIVCaseId() != null ? input.getHIVCaseId() : oldPerson.getEharsId());

        return oldPerson;


    }

    public Person updatedSexAndBirth(Person oldPerson, UpdateSexAndBirth input) {
        oldPerson.setBirthGenderCd(
                input.birthGender() != null ? input.birthGender() : oldPerson.getBirthGenderCd());
        oldPerson.setCurrSexCd(input.currentGender() != null ? input.currentGender() : oldPerson.getCurrSexCd());
        oldPerson.setBirthTime(input.dateOfBirth() != null ? input.dateOfBirth() : oldPerson.getBirthTime());
        oldPerson.setAsOfDateSex(input.asOf() != null ? input.asOf() : oldPerson.getAsOfDateSex());
        oldPerson.setAgeReported(input.currentAge() != null ? input.currentAge() : oldPerson.getAgeReported());
        oldPerson.setAgeReportedTime(
                input.ageReportedTime() != null ? input.ageReportedTime() : oldPerson.getAgeReportedTime());
        oldPerson.setBirthCityCd(input.birthCity() != null ? input.birthCity() : oldPerson.getBirthCityCd());
        oldPerson.setBirthCntryCd(input.birthCntry() != null ? input.birthCntry() : oldPerson.getBirthCntryCd());
        oldPerson.setBirthStateCd(input.birthState() != null ? input.birthState() : oldPerson.getBirthStateCd());
        oldPerson
                .setBirthOrderNbr(input.birthOrderNbr() != null ? input.birthOrderNbr() : oldPerson.getBirthOrderNbr());
        oldPerson.setMultipleBirthInd(
                input.multipleBirth() != null ? input.multipleBirth() : oldPerson.getMultipleBirthInd());
        oldPerson.setSexUnkReasonCd(input.sexunknown() != null ? input.sexunknown() : oldPerson.getSexUnkReasonCd());
        oldPerson.setAdditionalGenderCd(input.additionalGender() != null ? input.additionalGender().toString()
                : oldPerson.getAdditionalGenderCd());
        oldPerson.setPreferredGenderCd(input.transGenderInfo() != null ? input.transGenderInfo().toString()
                : oldPerson.getPreferredGenderCd());
        return oldPerson;
    }

    public Person updatedMortality(Person oldPerson, UpdateMortality input) {


        oldPerson.setAsOfDateMorbidity(input.asOf() != null ? input.asOf() : oldPerson.getAsOfDateMorbidity());
        oldPerson.setDeceasedIndCd(input.deceased() != null ? input.deceased() : oldPerson.getDeceasedIndCd());
        oldPerson.setDeceasedTime(input.deceasedTime() != null ? input.deceasedTime() : oldPerson.getDeceasedTime());
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

        return race.toString().length() > 20 ? race.toString().substring(0, 20) : race.toString();

    }

    private PostalEntityLocatorParticipation setEntityLocatorParticipation(PostalLocator savedRecord, Person oldPerson,
            UpdateMortality input) {
        PostalEntityLocatorParticipation entityLocatorPart = null;
        Optional<EntityLocatorParticipation> result =
                entityLocatorPartRepository.findByEntityIdAndLocatorUid(oldPerson.getId(), savedRecord.getId());

        EntityLocatorParticipationId entityLocatorPartId = new EntityLocatorParticipationId();
        entityLocatorPartId.setEntityUid(oldPerson.getNbsEntity().getId());
        entityLocatorPartId.setLocatorUid(savedRecord.getId());


        entityLocatorPart =
                result.isEmpty() ? new PostalEntityLocatorParticipation(oldPerson.getNbsEntity(), entityLocatorPartId,
                        new PatientCommand.AddAddress(
                                oldPerson.getId().longValue(),
                                savedRecord.getId().longValue(),
                                null,
                                null,
                                new City(input.cityOfDeath(), null),
                                input.stateOfDeath(),
                                null,
                                new County(input.countyOfDeath(), null),
                                new Country(input.countryOfDeath(), null),
                                null,
                                0L,
                                input.asOf()))
                        : (PostalEntityLocatorParticipation) result.get();
        entityLocatorPart.setCd("U");
        entityLocatorPart.setLastChgTime(Instant.now());
        entityLocatorPart.setAddReasonCd("ACTIVE");
        entityLocatorPart.setRecordStatusTime(Instant.now());
        entityLocatorPart.setStatusCd('A');
        entityLocatorPart.setStatusTime(Instant.now());
        entityLocatorPart.setUseCd(Constants.USE_CD);
        entityLocatorPart.setAsOfDate(input.asOf());

        return entityLocatorPart;
    }


}
