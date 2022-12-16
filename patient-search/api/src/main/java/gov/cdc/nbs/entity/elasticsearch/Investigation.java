package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.config.ElasticSearchConfig.DATE_PATTERN;

import java.time.Instant;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import gov.cdc.nbs.entity.enums.converter.InstantConverter;
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
    @Id
    private String id;
    @Field(name = "last_change", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant lastChange;
    @Field(name = "program_jurisdiction_oid", type = FieldType.Long)
    private Long programJurisdictionOid;
    @Field(name = "case_type_cd", type = FieldType.Keyword)
    private String caseTypeCd;
    @Field(name = "mood_cd", type = FieldType.Keyword)
    private String moodCd;
    @Field(name = "cd_desc_txt", type = FieldType.Text)
    private String cdDescTxt;
    @Field(name = "prog_area_cd", type = FieldType.Keyword)
    private String prog_area_cd;
    @Field(name = "jurisdiction_cd", type = FieldType.Long)
    private Long jurisdictionCd;
    @Field(name = "pregnant_ind_cd", type = FieldType.Keyword)
    private String pregnantIndCd;
    @Field(name = "act_id_seq", type = FieldType.Integer)
    private Integer actIdSeq;
    @Field(name = "root_extension_txt", type = FieldType.Text)
    private String rootExtensionTxt;
    @Field(name = "act_id_type_cd", type = FieldType.Keyword)
    private String actIdTypeCd;
    @Field(name = "act_id_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant actIdLastChgTime;
    @Field(name = "public_health_case_local_id", type = FieldType.Keyword)
    private String publicHealthCaseLocalId;
    @Field(name = "notification_local_id", type = FieldType.Keyword)
    private String notificationLocalId;
    @Field(name = "rpt_form_cmplt_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant rptFormCmpltTime;
    @Field(name = "activity_to_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant activityToTime;
    @Field(name = "add_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant addTime;
    @Field(name = "activity_from_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant activityFromTime;
    @Field(name = "public_health_case_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant publicHealthCaseLastChgTime;
    @Field(name = "notification_add_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant notificationAddTime;
    @Field(name = "add_user_id", type = FieldType.Long)
    private Long addUserId;
    @Field(name = "last_chg_user_id", type = FieldType.Long)
    private Long lastChgUserId;
    @Field(name = "act_uid", type = FieldType.Long)
    private Long actUid;
    @Field(name = "participation_type_cd", type = FieldType.Keyword)
    private String participationTypeCd;
    @Field(name = "subject_entity_uid", type = FieldType.Long)
    private Long subjectEntityUid;
    @Field(name = "participation_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant participationLastChgTime;
    @Field(name = "person_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant personLastChgTime;
    @Field(name = "person_cd", type = FieldType.Keyword)
    private String personCd;
    @Field(name = "parent_uid", type = FieldType.Long)
    private Long parentUid;
    @Field(name = "person_record_status_cd", type = FieldType.Keyword)
    private String personRecordStatusCd;
    @Field(name = "investigation_status_cd", type = FieldType.Keyword)
    private String investigationStatusCd;
    @Field(name = "outbreak_name", type = FieldType.Text)
    private String outbreakName;
    @Field(name = "class_cd", type = FieldType.Keyword)
    private String classCd;
    @Field(name = "notification_record_status_cd", type = FieldType.Keyword)
    private String notificationRecordStatusCd;
    @Field(name = "notification_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant notificationLastChgTime;
    @Field(name = "curr_process_state_cd", type = FieldType.Keyword)
    private String currProcessStateCd;
}
