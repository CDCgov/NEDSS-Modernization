package gov.cdc.nbs.patientlistener.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.NestedAddress;
import gov.cdc.nbs.entity.elasticsearch.NestedEmail;
import gov.cdc.nbs.entity.elasticsearch.NestedName;
import gov.cdc.nbs.entity.elasticsearch.NestedPhone;
import gov.cdc.nbs.entity.elasticsearch.NestedRace;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;

public class PersonConverter {

    private PersonConverter() {}

    public static ElasticsearchPerson toElasticsearchPerson(final Person person) {
        return ElasticsearchPerson.builder()
                .id(person.getId().toString())
                .personUid(person.getId())
                .firstNm(person.getFirstNm())
                .lastNm(person.getLastNm())
                .middleNm(person.getMiddleNm())
                .addTime(person.getAddTime())
                .addUserId(person.getAddUserId())
                .birthTime(person.getBirthTime())
                .cd(person.getCd())
                .currSexCd(person.getCurrSexCd())
                .deceasedIndCd(person.getDeceasedIndCd())
                .deceasedTime(person.getDeceasedTime())
                .electronicInd(person.getElectronicInd())
                .ethnicGroupInd(person.getEthnicity().ethnicGroup())
                .lastChgTime(person.getLastChgTime())
                .maritalStatusCd(person.getMaritalStatusCd())
                .recordStatusCd(person.getRecordStatusCd())
                .statusCd(person.getStatusCd())
                .statusTime(person.getStatusTime())
                .localId(person.getLocalId())
                .versionCtrlNbr(person.getVersionCtrlNbr())
                .edxInd(person.getEdxInd())
                .dedupMatchInd(person.getDedupMatchInd())
                .address(getAddresses(person))
                .phone(getPhones(person))
                .email(getEmails(person))
                .name(getNames(person))
                .race(getRaces(person))
                .asOfDateGeneral(person.getAsOfDateGeneral())
                .asOfDateAdmin(person.getAsOfDateAdmin())
                .asOfDateSex(person.getAsOfDateSex())
                .description(person.getDescription())
                .build();
    }

    private static List<NestedRace> getRaces(Person person) {
        if (person.getRaces() == null || person.getRaces().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getRaces()
                .stream()
                .map(race -> NestedRace.builder()
                        .raceCd(race.getRaceCategoryCd())
                        .raceDescTxt(race.getRaceDescTxt())
                        .build())
                .toList();
    }

    private static List<NestedName> getNames(Person person) {
        if (person.getNames() == null || person.getNames().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNames()
                .stream()
                .map(n -> NestedName.builder()
                        .firstNm(n.getFirstNm())
                        .firstNmSndx(n.getFirstNmSndx())
                        .middleNm(n.getMiddleNm())
                        .lastNm(n.getLastNm())
                        .lastNmSndx(n.getLastNmSndx())
                        .nmPrefix(n.getNmPrefix())
                        .nmSuffix(n.getNmSuffix() != null ? n.getNmSuffix().toString() : null)
                        .build())
                .toList();
    }

    private static List<NestedEmail> getEmails(Person person) {
        if (person == null
                || person.getNbsEntity().getEntityLocatorParticipations() == null
                || person.getNbsEntity().getEntityLocatorParticipations().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .filter(elp -> Objects.equals(elp.getCd(), "NET"))
                .map(TeleEntityLocatorParticipation.class::cast)
                .map(PersonConverter::asEmail)
                .toList();
    }

    private static NestedEmail asEmail(final TeleEntityLocatorParticipation participation) {
        return NestedEmail.builder()
                .emailAddress(participation.getLocator().getEmailAddress())
                .build();
    }

    private static List<NestedPhone> getPhones(Person person) {
        if (person == null
                || person.getNbsEntity().getEntityLocatorParticipations() == null
                || person.getNbsEntity().getEntityLocatorParticipations().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .filter(elp -> !Objects.equals(elp.getCd(), "NET"))
                .map(TeleEntityLocatorParticipation.class::cast)
                .map(PersonConverter::asPhone)
                .toList();
    }

    private static NestedPhone asPhone(final TeleEntityLocatorParticipation participation) {
        TeleLocator locator = participation.getLocator();
        return NestedPhone.builder()
                .telephoneNbr(locator.getPhoneNbrTxt())
                .extensionTxt(locator.getExtensionTxt())
                .build();
    }

    private static List<NestedAddress> getAddresses(Person person) {
        if (person == null
                || person.getNbsEntity().getEntityLocatorParticipations() == null
                || person.getNbsEntity().getEntityLocatorParticipations().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(PostalEntityLocatorParticipation.class::isInstance)
                .map(PostalEntityLocatorParticipation.class::cast)
                .map(PersonConverter::asAddress)
                .toList();

    }

    private static NestedAddress asAddress(final PostalEntityLocatorParticipation participation) {
        PostalLocator locator = participation.getLocator();

        return NestedAddress.builder()
                .streetAddr1(locator.getStreetAddr1())
                .streetAddr2(locator.getStreetAddr2())
                .city(locator.getCityCd())
                .state(locator.getStateCd())
                .zip(locator.getZipCd())
                .cntyCd(locator.getCntyCd())
                .cntryCd(locator.getCntryCd())
                .build();
    }

}
