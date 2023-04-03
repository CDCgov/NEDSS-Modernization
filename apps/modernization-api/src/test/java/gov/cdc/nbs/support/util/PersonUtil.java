package gov.cdc.nbs.support.util;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.patient.input.GeneralInfoInput;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.message.patient.input.SexAndBirthInput;
import gov.cdc.nbs.message.patient.input.PatientInput.Name;
import gov.cdc.nbs.message.patient.input.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.patient.input.PatientInput.PhoneType;
import gov.cdc.nbs.message.patient.input.PatientInput.PostalAddress;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static PatientInput convertToPatientInput(Person person) {
        var input = new PatientInput();
        input.setNames(Arrays.asList(
                new Name(person.getFirstNm(),
                        person.getMiddleNm(),
                        person.getLastNm(),
                        person.getNmSuffix(), null)));

        input.setSsn(person.getSsn());
        input.setDateOfBirth(LocalDate.ofInstant(person.getBirthTime(), ZoneId.systemDefault()));
        input.setBirthGender(person.getBirthGenderCd());
        input.setCurrentGender(person.getBirthGenderCd());
        input.setDeceased(person.getDeceasedIndCd());
        input.setEthnicityCode(person.getEthnicGroupInd());

        var elpList = person.getNbsEntity().getEntityLocatorParticipations();
        asAddresses(elpList).forEach(input.getAddresses()::add);
        asPhoneNumbers(elpList).forEach(input.getPhoneNumbers()::add);
        return input;
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

    private static Stream<PostalAddress> asAddresses(final Collection<EntityLocatorParticipation> participations) {
        return participations.stream().mapMulti((participation, consumer) -> {
            if (participation instanceof PostalEntityLocatorParticipation postal) {
                consumer.accept(asAddress(postal));
            }
        });
    }


    private static PostalAddress asAddress(final PostalEntityLocatorParticipation participation) {
        PostalLocator pl = participation.getLocator();

        return new PostalAddress(
                pl.getStreetAddr1(),
                pl.getStreetAddr2(),
                pl.getCityDescTxt(),
                pl.getStateCd(),
                pl.getCntyCd(),
                pl.getCntryCd(),
                pl.getZipCd(),
                pl.getCensusTract());
    }

    private static Stream<PhoneNumber> asPhoneNumbers(final Collection<EntityLocatorParticipation> participations) {
        return participations.stream().mapMulti((participation, consumer) -> {
            if (participation instanceof TeleEntityLocatorParticipation telecom) {
                consumer.accept(asPhoneNumber(telecom));
            }
        });
    }

    private static PhoneNumber asPhoneNumber(final TeleEntityLocatorParticipation participation) {
        TeleLocator tl = participation.getLocator();
        var phoneType = derivePhoneTypeFromElp(participation);
        return new PhoneNumber(tl.getPhoneNbrTxt(), tl.getExtensionTxt(), phoneType);
    }

    private static PhoneType derivePhoneTypeFromElp(EntityLocatorParticipation elp) {
        return switch (elp.getUseCd()) {
            case "MC" -> PhoneType.CELL;
            case "H" -> PhoneType.HOME;
            case "WP" -> PhoneType.WORK;
            default -> throw new IllegalArgumentException(
                    "Unable to derive phone type from EntityLocatorParticipation.use: " + elp.getUseCd());
        };
    }
}
