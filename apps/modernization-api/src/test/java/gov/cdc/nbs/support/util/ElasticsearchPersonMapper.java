package gov.cdc.nbs.support.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.codec.language.Soundex;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.NestedAddress;
import gov.cdc.nbs.entity.elasticsearch.NestedEmail;
import gov.cdc.nbs.entity.elasticsearch.NestedEntityId;
import gov.cdc.nbs.entity.elasticsearch.NestedName;
import gov.cdc.nbs.entity.elasticsearch.NestedPhone;
import gov.cdc.nbs.entity.elasticsearch.NestedRace;
import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;

public class ElasticsearchPersonMapper {

    public static List<ElasticsearchPerson> getElasticSearchPersons(List<Person> personList) {
        return personList.stream()
                .map(ElasticsearchPersonMapper::getElasticSearchPerson)
                .collect(Collectors.toList());
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
        race.setRaceDescTxt(person.getRaceCd());

        var nestedEntityIds = new ArrayList<NestedEntityId>();
        if (person.getEntityIds() != null && !person.getEntityIds().isEmpty()) {
            EntityId entityId = person.getEntityIds().get(0);
            var nestedEntityId = new NestedEntityId();
            nestedEntityId.setRecordStatusCd(entityId.getRecordStatusCd());
            nestedEntityId.setRootExtensionTxt(entityId.getRootExtensionTxt());
            nestedEntityId.setTypeCd(entityId.getTypeCd());
            nestedEntityIds.add(nestedEntityId);
        }


        var address = new NestedAddress();
        PostalLocator pl = PersonUtil.getPostalLocators(person).get(0);
        address.setStreetAddr1(pl.getStreetAddr1());
        address.setStreetAddr2(pl.getStreetAddr2());
        address.setCity(pl.getCityDescTxt());
        address.setState(pl.getStateCd());
        address.setCntyCd(pl.getCntyCd());
        address.setCntryCd(pl.getCntryCd());
        address.setZip(pl.getZipCd());

        ElasticsearchInstantValueConverter instantConverter = new ElasticsearchInstantValueConverter();
        return ElasticsearchPerson.builder()
                .id(String.valueOf(id))
                .personUid(id)
                .firstNm(person.getFirstNm())
                .lastNm(person.getLastNm())
                .name(List.of(name))
                .ssn(person.getSsn())
                .birthGenderCd(person.getBirthGenderCd())
                .currSexCd(person.getCurrSexCd())
                .email(asNestedEmails(person))
                .phone(asNestedPhones(person))
                .localId(person.getLocalId())
                .address(asNestedAddresses(person))
                .entityId(nestedEntityIds)
                .recordStatusCd(person.getRecordStatusCd())
                .ethnicGroupInd(person.getEthnicGroupInd())
                .birthTime((Instant) instantConverter.read(person.getBirthTime()))
                .race(asRaces(person.getRaces()))
                .deceasedIndCd(person.getDeceasedIndCd())
                .cd(person.getCd())
                .build();
    }

    private static List<NestedRace> asRaces(final Collection<PersonRace> races) {
        return races.stream().map(ElasticsearchPersonMapper::asRace).toList();
    }

    private static NestedRace asRace(final PersonRace race) {
        NestedRace nested = new NestedRace();
        nested.setRaceCd(race.getRaceCd());

        String description = race.getRaceDescTxt();

        nested.setRaceDescTxt(description == null ? race.getRaceCd() : description);

        return nested;
    }

    private static List<NestedEmail> asNestedEmails(Person person) {
        return person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .filter(participation -> Objects.equals("NET", participation.getCd()))
                .map(elp -> (TeleLocator) elp.getLocator())
                .map(ElasticsearchPersonMapper::asNestedEmail)
                .collect(Collectors.toList());
    }

    private static NestedEmail asNestedEmail(final TeleLocator locator) {
        NestedEmail nested = new NestedEmail();
        nested.setEmailAddress(locator.getEmailAddress());

        return nested;
    }

    private static List<NestedPhone> asNestedPhones(Person person) {
        return person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .filter(participation -> !Objects.equals("NET", participation.getCd()))
                .map(elp -> (TeleLocator) elp.getLocator())
                .map(ElasticsearchPersonMapper::asNestedPhone)
                .collect(Collectors.toList());
    }

    private static NestedPhone asNestedPhone(final TeleLocator locator) {
        NestedPhone nested = new NestedPhone();
        nested.setTelephoneNbr(locator.getPhoneNbrTxt().replaceAll("[\\D.]", ""));
        nested.setExtensionTxt(locator.getExtensionTxt().replaceAll("[\\D.]", ""));
        return nested;
    }

    private static List<NestedAddress> asNestedAddresses(Person person) {
        return person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(PostalEntityLocatorParticipation.class::isInstance)
                .map(elp -> (PostalLocator) elp.getLocator())
                .map(ElasticsearchPersonMapper::asNestedAddress)
                .collect(Collectors.toList());
    }

    private static NestedAddress asNestedAddress(final PostalLocator locator) {
        NestedAddress nested = new NestedAddress();
        nested.setStreetAddr1(locator.getStreetAddr1());
        nested.setStreetAddr2(locator.getStreetAddr2());
        nested.setCity(locator.getCityDescTxt());
        nested.setState(locator.getStateCd());
        nested.setCntyCd(locator.getCntyCd());
        nested.setCntryCd(locator.getCntryCd());
        nested.setZip(locator.getZipCd());
        return nested;
    }
}
