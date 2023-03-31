package gov.cdc.nbs.patientlistener.util;


import gov.cdc.nbs.entity.elasticsearch.*;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PersonUtil {

  private PersonUtil() {
  }

  public static List<ElasticsearchPerson> getElasticSearchPersons(List<Person> personList) {
    return personList.stream().flatMap(p -> Stream.of(getElasticSearchPerson(p))).toList();

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
    if (PersonUtil.getPostalLocators(person) != null && !PersonUtil.getPostalLocators(person).isEmpty()) {
      PostalLocator pl = PersonUtil.getPostalLocators(person).get(0);
      address.setStreetAddr1(pl.getStreetAddr1());
      address.setStreetAddr2(pl.getStreetAddr2());

      address.setCity(pl.getCityDescTxt());
      address.setState(pl.getStateCd());
      address.setCntyCd(pl.getCntyCd());
      address.setCntryCd(pl.getCntryCd());
      address.setZip(pl.getZipCd());
    }

    var phone = new NestedPhone();
    if (PersonUtil.getTeleLocators(List.of(person)) != null && !PersonUtil.getTeleLocators(List.of(person)).isEmpty()) {
      phone.setTelephoneNbr(PersonUtil.getTeleLocators(List.of(person)).get(0).getPhoneNbrTxt().replaceAll("\\D", ""));
    }

    var email = new NestedEmail();
    email.setEmailAddress(person.getHmEmailAddr());

    return ElasticsearchPerson.builder()
        .id(String.valueOf(id))
        .personUid(id)
        .firstNm(person.getFirstNm())
        .lastNm(person.getLastNm())
        .name(List.of(name))
        .ssn(person.getSsn())
        .birthGenderCd(person.getBirthGenderCd())
        .email(List.of(email))
        .phone(List.of(phone))
        .address(List.of(address))
        .recordStatusCd(person.getRecordStatusCd())
        .ethnicGroupInd(person.getEthnicGroupInd())
        .birthTime(person.getBirthTime())
        .race(List.of(race))
        .deceasedIndCd(person.getDeceasedIndCd())
        .cd(person.getCd())
        .build();
  }

  public static List<TeleLocator> getTeleLocators(List<Person> personList) {
    return personList.stream().flatMap(p -> getTeleLocators(p).stream()).toList();
  }

  public static List<PostalLocator> getPostalLocators(List<Person> personList) {
    return personList.stream().flatMap(p -> getPostalLocators(p).stream()).toList();
  }


  public static List<TeleLocator> getTeleLocators(Person person) {
    if (person.getNbsEntity() == null || person.getNbsEntity().getEntityLocatorParticipations() == null ||
        person.getNbsEntity().getEntityLocatorParticipations().isEmpty()) {
      return new ArrayList<>();
    }
    return person.getNbsEntity().getEntityLocatorParticipations().stream()
        .filter(elp -> elp.getCd().equals("TELE"))
        .map(elp -> (TeleLocator) elp.getLocator())
        .toList();
  }

  public static List<PostalLocator> getPostalLocators(Person person) {
    if (person.getNbsEntity() == null || person.getNbsEntity().getEntityLocatorParticipations() == null ||
        person.getNbsEntity().getEntityLocatorParticipations().isEmpty()) {
      return new ArrayList<>();
    }
    return person.getNbsEntity().getEntityLocatorParticipations().stream()
        .filter(elp -> elp.getCd().equals("PST"))
        .map(elp -> (PostalLocator) elp.getLocator())
        .toList();
  }

}
