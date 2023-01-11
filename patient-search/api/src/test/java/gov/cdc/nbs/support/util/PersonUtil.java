package gov.cdc.nbs.support.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.NestedAddress;
import gov.cdc.nbs.entity.elasticsearch.NestedEmail;
import gov.cdc.nbs.entity.elasticsearch.NestedName;
import gov.cdc.nbs.entity.elasticsearch.NestedPhone;
import gov.cdc.nbs.entity.elasticsearch.NestedRace;
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

    public static List<ElasticsearchPerson> getElasticSearchPersons(List<Person> personList) {
        return personList.stream().flatMap(p -> Stream.of(getElasticSearchPerson(p))).collect(Collectors.toList());
    }

    public static ElasticsearchPerson getElasticSearchPerson(Person person) {
        Long id = person.getId();

        var name = new NestedName();
        name.setFirstNm(person.getFirstNm());
        name.setLastNm(person.getLastNm());

        var race = new NestedRace();
        race.setRaceCd(person.getRaceCd());
        race.setRaceDescTxt(person.getRaceDescTxt());

        var address = new NestedAddress();
        PostalLocator pl = PersonUtil.getPostalLocators(person).get(0);
        address.setStreetAddr1(pl.getStreetAddr1());
        address.setStreetAddr2(pl.getStreetAddr2());
        address.setCity(pl.getCityDescTxt());
        address.setState(pl.getStateCd());
        address.setCntyCd(pl.getCntyCd());
        address.setCntryCd(pl.getCntryCd());
        address.setZip(pl.getZipCd());

        var phone = new NestedPhone();
        phone.setTelephoneNbr(PersonUtil.getTeleLocators(person).get(0).getPhoneNbrTxt());

        var email = new NestedEmail();
        email.setEmailAddress(person.getHmEmailAddr());

        return ElasticsearchPerson.builder()
                .id(String.valueOf(id))
                .personUid(id)
                .name(Arrays.asList(name))
                .ssn(person.getSsn())
                .birthGenderCd(person.getBirthGenderCd())
                .email(Arrays.asList(email))
                .phone(Arrays.asList(phone))
                .address(Arrays.asList(address))
                .recordStatusCd(person.getRecordStatusCd())
                .ethnicGroupInd(person.getEthnicGroupInd())
                .birthTime(person.getBirthTime())
                .race(Arrays.asList(race))
                .deceasedIndCd(person.getDeceasedIndCd())
                .cd(person.getCd())
                .build();
    }

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
