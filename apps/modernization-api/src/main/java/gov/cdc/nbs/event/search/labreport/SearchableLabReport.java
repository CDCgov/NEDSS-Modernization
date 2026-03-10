package gov.cdc.nbs.event.search.labreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.cdc.nbs.search.LocalDateWithTimeJsonDeserializer;
import gov.cdc.nbs.search.LocalDateWithTimeJsonSerializer;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public record SearchableLabReport(
    @JsonProperty("observation_uid") long identifier,
    @JsonProperty("class_cd") String classCode,
    @JsonProperty("mood_cd") String mood,
    @JsonProperty("program_area_cd") String programArea,
    @JsonProperty("jurisdiction_cd") String jurisdiction,
    @JsonProperty("program_jurisdiction_oid") long oid,
    @JsonProperty("pregnant_ind_cd") String pregnancyStatus,
    @JsonProperty("local_id") String local,
    @JsonProperty("activity_to_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate reportedOn,
    @JsonProperty("effective_from_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate collectedOn,
    @JsonProperty("rpt_to_state_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate receivedOn,
    @JsonProperty("add_user_id") long createdBy,
    @JsonProperty("add_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate createdOn,
    @JsonProperty("last_chg_user_id") long updatedBy,
    @JsonProperty("observation_last_chg_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate updatedOn,
    @JsonProperty("version_ctrl_nbr") long version,
    @JsonProperty("record_status_cd") String status,
    @JsonProperty("electronic_ind") String electronicEntry,
    @JsonProperty("person_participations") List<Person> people,
    @JsonProperty("organization_participations") List<Organization> organizations,
    @JsonProperty("observations") List<LabTest> tests,
    @JsonProperty("act_ids") List<Identifier> identifiers,
    @JsonProperty("associated_investigations") List<Investigation> associated) {

  public SearchableLabReport(
      long identifier,
      String classCode,
      String mood,
      String programArea,
      String jurisdiction,
      long oid,
      String pregnancyStatus,
      String local,
      LocalDate reportedOn,
      LocalDate collectedOn,
      LocalDate receivedOn,
      long createdBy,
      LocalDate createdOn,
      long updatedBy,
      LocalDate updatedOn,
      long version,
      String status,
      String electronicEntry) {
    this(
        identifier,
        classCode,
        mood,
        programArea,
        jurisdiction,
        oid,
        pregnancyStatus,
        local,
        reportedOn,
        collectedOn,
        receivedOn,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        version,
        status,
        electronicEntry,
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList());
  }

  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.EXISTING_PROPERTY,
      property = "person_cd")
  @JsonSubTypes({
    @JsonSubTypes.Type(value = Person.Patient.class, name = "PAT"),
    @JsonSubTypes.Type(value = Person.Provider.class, name = "PRV")
  })
  public sealed interface Person {

    @JsonProperty("person_cd")
    String code();

    record Patient(
        @JsonProperty("person_parent_uid") long identifier,
        @JsonProperty("local_id") String local,
        @JsonProperty("type_cd") String type,
        @JsonProperty("subject_class_cd") String subjectType,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("curr_sex_cd") String gender,
        @JsonProperty("birth_time")
            @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
            @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
            LocalDate birthday)
        implements Person {
      @Override
      public String code() {
        return "PAT";
      }
    }

    record Provider(
        @JsonProperty("entity_id") long identifier,
        @JsonProperty("type_cd") String type,
        @JsonProperty("subject_class_cd") String subjectType,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName)
        implements Person {
      @Override
      public String code() {
        return "PRV";
      }
    }
  }

  public record Organization(
      @JsonProperty("entity_id") long identifier,
      @JsonProperty("type_cd") String type,
      @JsonProperty("subject_class_cd") String subjectType,
      @JsonProperty("name") String name) {}

  public record LabTest(
      @JsonProperty("cd_desc_txt") String name,
      @JsonProperty("display_name") String result,
      @JsonProperty("alt_cd") String alternative) {}

  public record Identifier(
      @JsonProperty("type_cd") String type,
      @JsonProperty("type_desc_txt") String description,
      @JsonProperty("root_extension_txt") String value) {}

  public record Investigation(
      @JsonProperty("local_id") String local, @JsonProperty("cd_desc_txt") String condition) {}
}
