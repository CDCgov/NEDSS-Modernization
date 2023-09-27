package gov.cdc.nbs.patient.search;


import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.NestedAddress;
import gov.cdc.nbs.entity.elasticsearch.NestedEmail;
import gov.cdc.nbs.entity.elasticsearch.NestedEntityId;
import gov.cdc.nbs.entity.elasticsearch.NestedName;
import gov.cdc.nbs.entity.elasticsearch.NestedPhone;
import gov.cdc.nbs.entity.elasticsearch.NestedRace;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import org.apache.commons.codec.language.Soundex;

import java.util.ArrayList;
import java.util.List;

class SearchablePatientConverter {

  static ElasticsearchPerson toSearchable(final Person person) {
    return new SearchablePatientConverter().asSearchable(person);
  }

  private final Soundex soundex;

  private SearchablePatientConverter() {
    this.soundex = new Soundex();
  }

  private ElasticsearchPerson asSearchable(final Person person) {
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
        .ssn(person.getSsn())
        .address(getAddresses(person))
        .phone(getPhones(person))
        .email(getEmails(person))
        .name(getNames(person))
        .race(getRaces(person))
        .asOfDateGeneral(person.getAsOfDateGeneral())
        .asOfDateAdmin(person.getAsOfDateAdmin())
        .asOfDateSex(person.getAsOfDateSex())
        .description(person.getDescription())
        .entityId(asIdentifications(person))
        .build();
  }

  private List<NestedRace> getRaces(Person person) {
    if (person.getRaces() == null || person.getRaces().isEmpty()) {
      return new ArrayList<>();
    }
    return person.getRaces()
        .stream()
        .map(race -> NestedRace.builder()
            .raceCd(race.getRaceCategoryCd())
            .raceCategoryCd(race.getRaceCategoryCd())
            .raceDescTxt(race.getRaceDescTxt())
            .build())
        .toList();
  }

  private List<NestedName> getNames(final Person person) {
    if (person.getNames() == null || person.getNames().isEmpty()) {
      return new ArrayList<>();
    }
    return person.getNames()
        .stream()
        .map(this::asName)
        .toList();
  }

  private NestedName asName(final PersonName name) {
    return NestedName.builder()
        .firstNm(name.getFirstNm())
        .firstNmSndx(soundex.encode(name.getFirstNm()))
        .middleNm(name.getMiddleNm())
        .lastNm(name.getLastNm())
        .lastNmSndx(soundex.encode(name.getLastNm()))
        .nmPrefix(name.getNmPrefix())
        .nmSuffix(name.getNmSuffix() != null ? name.getNmSuffix().toString() : null)
        .build();
  }

  private List<NestedEmail> getEmails(Person person) {
    return person.emailAddresses()
        .stream()
        .map(TeleEntityLocatorParticipation.class::cast)
        .map(SearchablePatientConverter::asEmail)
        .toList();
  }

  private static NestedEmail asEmail(final TeleEntityLocatorParticipation participation) {
    return NestedEmail.builder()
        .emailAddress(participation.getLocator().getEmailAddress())
        .build();
  }

  private static List<NestedPhone> getPhones(Person person) {
    return person.phoneNumbers()
        .stream()
        .map(SearchablePatientConverter::asPhone)
        .toList();
  }

  private static NestedPhone asPhone(final TeleEntityLocatorParticipation participation) {
    TeleLocator locator = participation.getLocator();
    String number = locator.getPhoneNbrTxt() == null ? null : locator.getPhoneNbrTxt().replaceAll("\\W", "");
    return NestedPhone.builder()
        .telephoneNbr(number)
        .extensionTxt(locator.getExtensionTxt())
        .build();
  }

  private static List<NestedAddress> getAddresses(Person person) {
    return person.getNbsEntity()
        .addresses()
        .stream()
        .map(SearchablePatientConverter::asAddress)
        .toList();

  }

  private static NestedAddress asAddress(final PostalEntityLocatorParticipation participation) {
    PostalLocator locator = participation.getLocator();

    return NestedAddress.builder()
        .streetAddr1(locator.getStreetAddr1())
        .streetAddr2(locator.getStreetAddr2())
        .city(locator.getCityDescTxt())
        .state(locator.getStateCd())
        .zip(locator.getZipCd())
        .cntyCd(locator.getCntyCd())
        .cntryCd(locator.getCntryCd())
        .build();
  }

  private static List<NestedEntityId> asIdentifications(final Person person) {
    return person.identifications()
        .stream()
        .map(SearchablePatientConverter::asIdentification)
        .toList();
  }

  private static NestedEntityId asIdentification(final EntityId identification) {
    String adjusted = identification.getRootExtensionTxt().replaceAll("\\W", "");
    return new NestedEntityId(
        identification.getRecordStatusCd(),
        adjusted,
        identification.getTypeCd()
    );
  }

}
