package gov.cdc.nbs.entity.elasticsearch;

import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import javax.persistence.Id;
import java.time.Instant;
import java.util.List;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "investigation")
public class Investigation {
  public static final String PROGRAM_JURISDICTION_OID = "program_jurisdiction_oid";
  public static final String CASE_CLASS_CD = "case_class_cd";
  public static final String OUTBREAK_NAME = "outbreak_name";
  public static final String CASE_TYPE_CD = "case_type_cd";
  public static final String CONDITION_ID = "condition";
  public static final String PROGRAM_AREA_CD = "prog_area_cd";
  public static final String JURISDICTION_CD = "jurisdiction_cd";
  public static final String PREGNANT_IND_CD = "pregnant_ind_cd";
  public static final String LOCAL_ID = "local_id";
  public static final String RPT_FORM_COMPLETE_TIME = "rpt_form_cmplt_time";
  public static final String ACTIVITY_TO_TIME = "activity_to_time";
  public static final String ACTIVITY_FROM_TIME = "activity_from_time";
  public static final String ADD_TIME = "add_time";
  public static final String PUBLIC_HEALTH_CASE_LAST_CHANGE_TIME = "public_health_case_last_chg_time";
  public static final String ADD_USER_ID = "add_user_id";
  public static final String LAST_CHANGE_USER_ID = "last_chg_user_id";
  public static final String CURR_PROCESS_STATUS_CD = "curr_process_state_cd";
  public static final String INVESTIGATION_STATUS_CD = "investigation_status_cd";
  public static final String MOOD_CD = "mood_cd";
  public static final String NOTIFICATION_LOCAL_ID = "notification_local_id";
  public static final String NOTIFICATION_ADD_TIME = "notification_add_time";
  public static final String NOTIFICATION_RECORD_STATUS_CD = "notification_record_status_cd";
  public static final String ACT_IDS = "act_ids";
  public static final String PERSON_PARTICIPATIONS = "person_participations";
  public static final String ORGANIZATION_PARTICIPATIONS = "organization_participations";

  /*
   * Same as public_health_case_uid
   */
  @Id
  private String id;

  @Field(name = "program_jurisdiction_oid", type = FieldType.Long)
  private Long programJurisdictionOid;

  @Field(name = "case_class_cd", type = FieldType.Keyword)
  private String caseClassCd;

  @Field(name = "outbreak_name", type = FieldType.Keyword)
  private String outbreakName;

  @Field(name = "case_type_cd", type = FieldType.Keyword)
  private String caseTypeCd;

  @Field(name = "cd_desc_txt", type = FieldType.Keyword)
  private String cdDescTxt;

  @Field(name = "condition", type = FieldType.Keyword)
  private String condition;

  @Field(name = "prog_area_cd", type = FieldType.Keyword)
  private String progAreaCd;

  @Field(name = "jurisdiction_cd", type = FieldType.Long)
  private Long jurisdictionCd;

  @Field(name = "jurisdiction_code_desc_txt", type = FieldType.Text)
  private String jurisdictionCodeDescTxt;

  @Field(name = "pregnant_ind_cd", type = FieldType.Keyword)
  private String pregnantIndCd;

  @Field(name = "local_id", type = FieldType.Keyword)
  private String localId;

  @Field(name = "rpt_form_cmplt_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant rptFormCmpltTime;

  @Field(name = "activity_to_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant activityToTime;

  @Field(name = "activity_from_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant activityFromTime;

  @Field(name = "add_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant addTime;

  @Field(name = "public_health_case_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant publicHealthCaseLastChgTime;

  @Field(name = "add_user_id", type = FieldType.Long)
  private Long addUserId;

  @Field(name = "last_chg_user_id", type = FieldType.Long)
  private Long lastChangeUserId;

  @Field(name = "curr_process_state_cd", type = FieldType.Keyword)
  private String currProcessStateCd;

  @Field(name = "investigation_status_cd", type = FieldType.Keyword)
  private String investigationStatusCd;

  @Field(name = "mood_cd", type = FieldType.Keyword)
  private String moodCd;

  @Field(name = "notification_local_id", type = FieldType.Keyword)
  private String notificationLocalId;

  @Field(name = "notification_add_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant notificationAddTime;

  @Field(name = "notification_record_status_cd", type = FieldType.Keyword)
  private String notificationRecordStatusCd;

  // nested fields
  @Field(name = "person_participations", type = FieldType.Nested)
  private List<ElasticsearchPersonParticipation> personParticipations;

  @Field(name = "organization_participations", type = FieldType.Nested)
  private List<ElasticsearchOrganizationParticipation> organizationParticipations;

  @Field(name = "act_ids", type = FieldType.Nested)
  private List<ElasticsearchActId> actIds;

}
