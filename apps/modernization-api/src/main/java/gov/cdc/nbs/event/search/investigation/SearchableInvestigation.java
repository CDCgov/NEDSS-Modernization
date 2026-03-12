package gov.cdc.nbs.event.search.investigation;

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

public record SearchableInvestigation(
    @JsonProperty("public_health_case_uid") long identifier,
    @JsonProperty("class_cd") String classCode,
    @JsonProperty("mood_cd") String mood,
    @JsonProperty("prog_area_cd") String programArea,
    @JsonProperty("jurisdiction_cd") String jurisdiction,
    @JsonProperty("jurisdiction_code_desc_txt") String jurisdictionName,
    @JsonProperty("program_jurisdiction_oid") long oid,
    @JsonProperty("case_class_cd") String caseClass,
    @JsonProperty("case_type_cd") String caseType,
    @JsonProperty("outbreak_name") String outbreak,
    @JsonProperty("cd_desc_txt") String conditionName,
    @JsonProperty("condition") String condition,
    @JsonProperty("pregnant_ind_cd") String pregnancyStatus,
    @JsonProperty("local_id") String local,
    @JsonProperty("add_user_id") long createdBy,
    @JsonProperty("add_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate createdOn,
    @JsonProperty("last_chg_user_id") long updatedBy,
    @JsonProperty("public_health_case_last_chg_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate updatedOn,
    @JsonProperty("rpt_form_cmplt_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate reportedOn,
    @JsonProperty("activity_from_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate startedOn,
    @JsonProperty("activity_to_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate closedOn,
    @JsonProperty("curr_process_state_cd") String processing,
    @JsonProperty("investigation_status_cd") String status,
    @JsonProperty("notification_local_id") String notification,
    @JsonProperty("notification_add_time")
        @JsonSerialize(using = LocalDateWithTimeJsonSerializer.class)
        @JsonDeserialize(using = LocalDateWithTimeJsonDeserializer.class)
        LocalDate notifiedOn,
    @JsonProperty("notification_record_status_cd") String notificationStatus,
    @JsonProperty("investigator_last_nm") String investigatorLastName,
    @JsonProperty("person_participations") List<Person> people,
    @JsonProperty("organization_participations") List<Organization> organizations,
    @JsonProperty("act_ids") List<Identifier> identifiers) {

  public static final String IDENTIFICATION = "act_ids";
  public static final String IDENTIFICATION_TYPE = "act_ids.type_cd";
  public static final String IDENTIFICATION_SEQUENCE = "act_ids.act_id_seq";
  public static final String IDENTIFICATION_VALUE = "act_ids.root_extension_txt";

  public static final String PERSON = "person_participations";
  public static final String PERSON_TYPE = "person_participations.type_cd";
  public static final String PERSON_SUBJECT = "person_participations.subject_class_cd";
  public static final String PERSON_IDENTIFIER = "person_participations.entity_id";

  public static final String ORGANIZATION = "organization_participations";
  public static final String ORGANIZATION_TYPE = "organization_participations.type_cd";
  public static final String ORGANIZATION_IDENTIFIER = "organization_participations.entity_id";

  public SearchableInvestigation(
      long identifier,
      String classCode,
      String mood,
      String programArea,
      String jurisdiction,
      String jurisdictionName,
      long oid,
      String caseClass,
      String caseType,
      String outbreak,
      String conditionName,
      String condition,
      String pregnancyStatus,
      String local,
      long createdBy,
      LocalDate createdOn,
      long updatedBy,
      LocalDate updatedOn,
      LocalDate reportedOn,
      LocalDate startedOn,
      LocalDate closedOn,
      String processing,
      String status,
      String notification,
      LocalDate notifiedOn,
      String notificationStatus,
      String investigatorLastName) {
    this(
        identifier,
        classCode,
        mood,
        programArea,
        jurisdiction,
        jurisdictionName,
        oid,
        caseClass,
        caseType,
        outbreak,
        conditionName,
        condition,
        pregnancyStatus,
        local,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        reportedOn,
        startedOn,
        closedOn,
        processing,
        status,
        notification,
        notifiedOn,
        notificationStatus,
        investigatorLastName,
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList());
  }

  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.EXISTING_PROPERTY,
      property = "person_cd")
  @JsonSubTypes({
    @JsonSubTypes.Type(value = SearchableInvestigation.Person.Patient.class, name = "PAT"),
    @JsonSubTypes.Type(value = SearchableInvestigation.Person.Provider.class, name = "PRV")
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
      @JsonProperty("entity_id") long identifier, @JsonProperty("type_cd") String type) {}

  public record Identifier(
      @JsonProperty("act_id_seq") int sequence,
      @JsonProperty("type_cd") String type,
      @JsonProperty("root_extension_txt") String value) {}
}
