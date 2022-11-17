package gov.cdc.nbs.support.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.graphql.input.PatientInput;
import gov.cdc.nbs.graphql.input.PatientInput.Name;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneNumber;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneType;
import gov.cdc.nbs.graphql.input.PatientInput.PostalAddress;;

public class PersonUtil {

    public static List<TeleLocator> getTeleLocators(List<Person> personList) {
        return personList.stream().flatMap(p -> getTeleLocators(p).stream()).collect(Collectors.toList());
    }

    public static List<PostalLocator> getPostalLocators(List<Person> personList) {
        return personList.stream().flatMap(p -> getPostalLocators(p).stream()).collect(Collectors.toList());
    }

    public static List<TeleLocator> getTeleLocators(Person person) {
        return person.getNBSEntity().getEntityLocatorParticipations().stream()
                .filter(elp -> elp.getClassCd().equals("TELE"))
                .map(elp -> (TeleLocator) elp.getLocator())
                .collect(Collectors.toList());
    }

    public static List<PostalLocator> getPostalLocators(Person person) {
        return person.getNBSEntity().getEntityLocatorParticipations().stream()
                .filter(elp -> elp.getClassCd().equals("PST"))
                .map(elp -> (PostalLocator) elp.getLocator())
                .collect(Collectors.toList());
    }

    public static PatientInput convertToPatientInput(Person person) {
        var input = new PatientInput();
        input.setName(
                new Name(person.getFirstNm(),
                        person.getMiddleNm(),
                        person.getLastNm(),
                        person.getNmSuffix()));

        input.setSsn(person.getSsn());
        input.setDateOfBirth(person.getBirthTime());
        input.setBirthGender(person.getBirthGenderCd());
        input.setCurrentGender(person.getBirthGenderCd());
        input.setDeceased(person.getDeceasedIndCd());
        input.setEthnicity(person.getEthnicGroupInd());

        var elpList = person.getNBSEntity().getEntityLocatorParticipations();
        if (elpList != null && elpList.size() > 0) {
            input.setAddresses(new ArrayList<>());
            input.setPhoneNumbers(new ArrayList<>());
            elpList.forEach(elp -> {
                // Phone
                if (elp.getClassCd().equals("TELE")) {
                    var tl = (TeleLocator) elp.getLocator();
                    var phoneType = derivePhoneTypeFromElp(elp);
                    var phoneNumber = new PhoneNumber(tl.getPhoneNbrTxt(), tl.getExtensionTxt(), phoneType);
                    input.getPhoneNumbers().add(phoneNumber);
                } // Address
                else if (elp.getClassCd().equals("PST")) {
                    var pl = (PostalLocator) elp.getLocator();
                    var pa = new PostalAddress(pl.getStreetAddr1(),
                            pl.getStreetAddr2(),
                            pl.getCityDescTxt(),
                            pl.getStateCd(),
                            pl.getCntyCd(),
                            pl.getCntryCd(),
                            pl.getZipCd(),
                            pl.getCensusTract());
                    input.getAddresses().add(pa);
                }
            });
        }
        return input;
    }

    private static PhoneType derivePhoneTypeFromElp(EntityLocatorParticipation elp) {
        switch (elp.getUseCd()) {
            case "MC":
                return PhoneType.CELL;
            case "H":
                return PhoneType.HOME;
            case "WP":
                return PhoneType.WORK;
            default:
                throw new IllegalArgumentException(
                        "Unable to derive phone type from EntityLocatorParticipation.useCd: " + elp.getUseCd());
        }
    }
}
