package gov.cdc.nbs.support.util;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.patient.input.GeneralInfoInput;
import gov.cdc.nbs.message.patient.input.SexAndBirthInput;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class PersonUtil {

    public static List<TeleLocator> getTeleLocators(Person person) {
        return person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .map(elp -> (TeleLocator) elp.getLocator())
                .collect(Collectors.toList());
    }


    public static List<PostalLocator> getPostalLocators(Person person) {
        return person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(PostalEntityLocatorParticipation.class::isInstance)
                .map(elp -> (PostalLocator) elp.getLocator())
                .collect(Collectors.toList());
    }

    public static GeneralInfoInput convertToGeneralInput(Person person) {
        var input = new GeneralInfoInput();
        input.setPatientId(person.getId());
        input.setAsOf(person.getAsOfDateGeneral());
        input.setMaritalStatus(person.getMaritalStatusCd());
        input.setMothersMaidenName(person.getMothersMaidenNm());
        input.setAdultsInHouseNumber(person.getAdultsInHouseNbr());
        input.setChildrenInHouseNumber(person.getChildrenInHouseNbr());
        input.setOccupationCode(person.getOccupationCd());
        input.setEducationLevelCode(person.getEducationLevelCd());
        input.setPrimaryLanguageCode(person.getPrimLangCd());
        input.setSpeaksEnglishCode(person.getSpeaksEnglishCd());
        input.setEharsId(person.getEharsId());
        return input;
    }

    public static SexAndBirthInput convertToSexAndBirthInput(Person person) {
        var input = new SexAndBirthInput();
        input.setAsOf(Instant.now());
        input.setPatientId(person.getId());
        input.setDateOfBirth(LocalDate.ofInstant(person.getBirthTime(), ZoneId.systemDefault()));
        input.setBirthGender(person.getBirthGenderCd());
        input.setCurrentGender(person.getCurrSexCd());
        input.setAdditionalGender(person.getAdditionalGenderCd());
        input.setTransGenderInfo(person.getPreferredGenderCd());
        input.setBirthCity(person.getBirthCityCd());
        input.setBirthCntry(person.getBirthCntryCd());
        input.setBirthState(person.getBirthStateCd());
        input.setBirthOrderNbr(person.getBirthOrderNbr());
        input.setMultipleBirth(person.getMultipleBirthInd());
        input.setSexUnknown(person.getSexUnkReasonCd());
        input.setCurrentAge(person.getAgeReported());
        input.setAgeReportedTime(person.getAgeReportedTime());
        return input;
    }


}
