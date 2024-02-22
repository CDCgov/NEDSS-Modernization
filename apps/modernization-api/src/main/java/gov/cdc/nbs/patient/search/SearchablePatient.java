package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.cdc.nbs.search.LocalDateWithTimeJsonDeserializer;
import gov.cdc.nbs.search.LocalDateWithTimeJsonSerializer;
import gov.cdc.nbs.search.WithoutSpecialCharactersJsonSerializer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public record SearchablePatient(
    @JsonProperty("person_uid")
    long identifier,
    @JsonProperty("local_id")
    String local,
    @JsonProperty("record_status_cd")
    String status,
    @JsonProperty("birth_time")
    @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
    @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
    LocalDate birthday,
    @JsonProperty("deceased_ind_cd")
    String deceased,
    @JsonProperty("curr_sex_cd")
    String gender,
    @JsonProperty("ethnic_group_ind")
    String ethnicity,
    @JsonProperty("name")
    List<Name> names,
    @JsonProperty("address")
    List<Address> addresses,
    @JsonProperty("phone")
    List<Phone> phones,
    @JsonProperty("email")
    List<Email> emails,
    @JsonProperty("race")
    List<Race> races,
    @JsonProperty("entity_id")
    List<Identification> identifications
) {

  public SearchablePatient(
      long identifier,
      String local,
      String status,
      LocalDate birthday,
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
      @JsonSerialize(using = WithoutSpecialCharactersJsonSerializer.class, as = String.class)
      String first,
      @JsonProperty("firstNmSndx")
      String firstSoundex,
      @JsonProperty("middleNm")
      @JsonSerialize(using = WithoutSpecialCharactersJsonSerializer.class, as = String.class)
      String middle,
      @JsonProperty("lastNm")
      @JsonSerialize(using = WithoutSpecialCharactersJsonSerializer.class, as = String.class)
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
      @JsonSerialize(using = WithoutSpecialCharactersJsonSerializer.class, as = String.class)
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
      @JsonSerialize(using = WithoutSpecialCharactersJsonSerializer.class, as = String.class)
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

  @JsonProperty("cd")
  String type() {
    return "PAT";
  }
}
