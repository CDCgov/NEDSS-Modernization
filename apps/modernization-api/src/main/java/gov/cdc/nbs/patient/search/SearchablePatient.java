package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;

public record SearchablePatient(
    @JsonProperty("person_uid")
    long identifier,
    @JsonProperty("local_id")
    String local,
    @JsonProperty("record_status_cd")
    String status,
    @JsonProperty("birth_time")
    String birthday,
    @JsonProperty("deceased_ind_cd")
    String deceased,
    @JsonProperty("curr_sex_cd")
    String gender,
    @JsonProperty("ethnic_group_ind")
    String ethnicity,
    @JsonProperty("name")
    Collection<Name> names,
    @JsonProperty("address")
    Collection<Address> addresses,
    @JsonProperty("phone")
    Collection<Phone> phones,
    @JsonProperty("email")
    Collection<Email> emails,
    @JsonProperty("race")
    Collection<Race> races,
    @JsonProperty("entity_id")
    Collection<Identification> identifications
) {

  public SearchablePatient(
      long identifier,
      String local,
      String status,
      String birthday,
      String deceased,
      String gender,
      String ethnicity
  ) {
    this(
        identifier,
        local,
        status,
        birthday,
        deceased,
        gender,
        ethnicity,
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList()
    );
  }

  public record Name(
      @JsonProperty("nm_use_cd")
      String use,
      @JsonProperty("firstNm")
      String first,
      @JsonProperty("firstNmSndx")
      String firstSoundex,
      @JsonProperty("middleNm")
      String middle,
      @JsonProperty("lastNm")
      String last,
      @JsonProperty("lastNmSndx")
      String lastSoundex,
      @JsonProperty("nmPrefix")
      String prefix,
      @JsonProperty("nmSuffix")
      String suffix
  ) {
  }


  public record Address(
      @JsonProperty("streetAddr1")
      String address1,
      @JsonProperty("streetAddr2")
      String address2,
      @JsonProperty("city")
      String city,
      @JsonProperty("state")
      String state,
      @JsonProperty("zip")
      String zip,
      @JsonProperty("cntyCd")
      String county,
      @JsonProperty("cntryCd")
      String country
  ) {
  }


  public record Phone(
      @JsonProperty("telephoneNbr")
      String number,
      @JsonProperty("extensionTxt")
      String extension
  ) {

  }


  public record Email(
      @JsonProperty("emailAddress")
      String address
  ) {

  }


  public record Identification(
      @JsonProperty("typeCd")
      String type,
      @JsonProperty("rootExtensionTxt")
      String value,
      @JsonProperty("recordStatusCd")
      String status
  ) {
  }


  public record Race(
      @JsonProperty("raceCd")
      String category,
      @JsonProperty("raceCategoryCd")
      String detail
  ) {

  }
}
