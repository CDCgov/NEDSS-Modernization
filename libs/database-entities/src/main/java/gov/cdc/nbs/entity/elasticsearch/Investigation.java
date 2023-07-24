package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

import java.time.Instant;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "investigation")
public class Investigation {
    // List of all fields to assist building queries
    public static final String RECORD_STATUS = "record_status_cd";
    public static final String LAST_CHANGE_TIME = "last_change_time";
    public static final String PUBLIC_HEALTH_CASE_UID = "public_health_case_uid";
    public static final String PROGRAM_JURISDICTION_OID = "program_jurisdiction_oid";
    public static final String CASE_CLASS_CD = "case_class_cd";
    public static final String OUTBREAK_NAME = "outbreak_name";
    public static final String CASE_TYPE_CD = "case_type_cd";
    public static final String CD_DESC_TXT = "cd_desc_txt";
    public static final String CD = "cd";
    public static final String PROGRAM_AREA_CD = "prog_area_cd";
    public static final String JURISDICTION_CD = "jurisdiction_cd";
    public static final String JURISDICTION_CODE_DESC_TXT = "jurisdiction_code_desc_txt";
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
    public static final String NOTIFICATION_LAST_CHANGE_TIME = "notification_last_chg_time";
    public static final String ACT_IDS = "act_ids";
    public static final String PERSON_PARTICIPATIONS = "person_participations";
    public static final String ORGANIZATION_PARTICIPATIONS = "organization_participations";

    /*
     * Same as public_health_case_uid
     */
    @Id
    private String id;

    @Field(name = RECORD_STATUS, type = FieldType.Keyword)
    private String recordStatus;

    @Field(name = LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant lastChangeTime;

    @Field(name = PUBLIC_HEALTH_CASE_UID, type = FieldType.Long)
    private Long publicHealthCaseUid;

    @Field(name = PROGRAM_JURISDICTION_OID, type = FieldType.Long)
    private Long programJurisdictionOid;

    @Field(name = CASE_CLASS_CD, type = FieldType.Keyword)
    private String caseClassCd;

    @Field(name = OUTBREAK_NAME, type = FieldType.Keyword)
    private String outbreakName;

    @Field(name = CASE_TYPE_CD, type = FieldType.Keyword)
    private String caseTypeCd;

    @Field(name = CD_DESC_TXT, type = FieldType.Keyword)
    private String cdDescTxt;

    @Field(name = CD, type = FieldType.Keyword)
    private String cd;

    @Field(name = PROGRAM_AREA_CD, type = FieldType.Keyword)
    private String progAreaCd;

    @Field(name = JURISDICTION_CD, type = FieldType.Long)
    private Long jurisdictionCd;

    @Field(name = JURISDICTION_CODE_DESC_TXT, type = FieldType.Text)
    private String jurisdictionCodeDescTxt;

    @Field(name = PREGNANT_IND_CD, type = FieldType.Keyword)
    private String pregnantIndCd;

    @Field(name = LOCAL_ID, type = FieldType.Keyword)
    private String localId;

    @Field(name = RPT_FORM_COMPLETE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant rptFormCmpltTime;

    @Field(name = ACTIVITY_TO_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant activityToTime;

    @Field(name = ACTIVITY_FROM_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant activityFromTime;

    @Field(name = ADD_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant addTime;

    @Field(name = PUBLIC_HEALTH_CASE_LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant publicHealthCaseLastChgTime;

    @Field(name = ADD_USER_ID, type = FieldType.Long)
    private Long addUserId;

    @Field(name = LAST_CHANGE_USER_ID, type = FieldType.Long)
    private Long lastChangeUserId;

    @Field(name = CURR_PROCESS_STATUS_CD, type = FieldType.Keyword)
    private String currProcessStateCd;

    @Field(name = INVESTIGATION_STATUS_CD, type = FieldType.Keyword)
    private String investigationStatusCd;

    @Field(name = MOOD_CD, type = FieldType.Keyword)
    private String moodCd;

    @Field(name = NOTIFICATION_LOCAL_ID, type = FieldType.Keyword)
    private String notificationLocalId;

    @Field(name = NOTIFICATION_ADD_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant notificationAddTime;

    @Field(name = NOTIFICATION_RECORD_STATUS_CD, type = FieldType.Keyword)
    private String notificationRecordStatusCd;

    @Field(name = NOTIFICATION_LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant notificationLastChgTime;

    // nested fields
    @Field(name = PERSON_PARTICIPATIONS, type = FieldType.Nested)
    private List<ElasticsearchPersonParticipation> personParticipations;

    @Field(name = ORGANIZATION_PARTICIPATIONS, type = FieldType.Nested)
    private List<ElasticsearchOrganizationParticipation> organizationParticipations;

    @Field(name = ACT_IDS, type = FieldType.Nested)
    private List<ElasticsearchActId> actIds;

}
