package gov.cdc.nbs.support.util;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.NestedAddress;
import gov.cdc.nbs.entity.elasticsearch.NestedEmail;
import gov.cdc.nbs.entity.elasticsearch.NestedName;
import gov.cdc.nbs.entity.elasticsearch.NestedPhone;
import gov.cdc.nbs.entity.elasticsearch.NestedRace;
import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientInput.Name;
import gov.cdc.nbs.message.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.PatientInput.PhoneType;
import gov.cdc.nbs.message.PatientInput.PostalAddress;
import org.apache.commons.codec.language.Soundex;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonUtil {

    public static List<ElasticsearchPerson> getElasticSearchPersons(List<Person> personList) {
        return personList.stream().flatMap(p -> Stream.of(getElasticSearchPerson(p))).collect(Collectors.toList());
    }

    public static ElasticsearchPerson getElasticSearchPerson(Person person) {
        Long id = person.getId();

        Soundex soundex = new Soundex();
        var name = new NestedName();
        name.setFirstNm(person.getFirstNm());
        name.setFirstNmSndx(soundex.encode(person.getFirstNm()));
        name.setLastNm(person.getLastNm());
        name.setLastNmSndx(soundex.encode(person.getLastNm()));
 
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

        InstantConverter instantConverter = new InstantConverter();
        return ElasticsearchPerson.builder()
                .id(String.valueOf(id))
                .personUid(id)
                .firstNm(person.getFirstNm())
                .lastNm(person.getLastNm())
                .name(Arrays.asList(name))
                .ssn(person.getSsn())
                .birthGenderCd(person.getBirthGenderCd())
                .currSexCd(person.getCurrSexCd())
                .email(Arrays.asList(email))
                .phone(Arrays.asList(phone))
                .localId(person.getLocalId())
                .address(Arrays.asList(address))
                .recordStatusCd(person.getRecordStatusCd())
                .ethnicGroupInd(person.getEthnicGroupInd())
                .birthTime((Instant) instantConverter.read(person.getBirthTime()))
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
        input.setDateOfBirth(person.getBirthTime());
        input.setBirthGender(person.getBirthGenderCd());
        input.setCurrentGender(person.getBirthGenderCd());
        input.setDeceased(person.getDeceasedIndCd());
        input.setEthnicityCode(person.getEthnicGroupInd());

        var elpList = person.getNbsEntity().getEntityLocatorParticipations();
        asAddresses(elpList).forEach(input.getAddresses()::add);
        asPhoneNumbers(elpList).forEach(input.getPhoneNumbers()::add);
        return input;
    }

    private static Stream<PostalAddress> asAddresses(final Collection<EntityLocatorParticipation> participations) {
        return participations.stream().mapMulti((participation, consumer) -> {
            if(participation instanceof PostalEntityLocatorParticipation postal) {
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
                pl.getCensusTract()
       );
    }

    private static Stream<PhoneNumber> asPhoneNumbers(final Collection<EntityLocatorParticipation> participations) {
        return participations.stream().mapMulti((participation, consumer) -> {
            if(participation instanceof TeleEntityLocatorParticipation telecom) {
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
