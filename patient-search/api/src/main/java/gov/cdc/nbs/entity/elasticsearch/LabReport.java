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
@Document(indexName = "lab_report")
public class LabReport {
    @Id
    private String id;
    @Field(name = "last_change", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant lastChange;
    @Field(name = "class_cd", type = FieldType.Keyword)
    private String classCd;
    @Field(name = "mood_cd", type = FieldType.Keyword)
    private String moodCd;
    @Field(name = "type_desc_txt", type = FieldType.Keyword)
    private String typeDescTxt;
    @Field(name = "root_extension_txt", type = FieldType.Text)
    private String rootExtensionTxt;
    @Field(name = "program_jurisdiction_oid", type = FieldType.Long)
    private Long programJurisdictionOid;
    @Field(name = "program_area_cd", type = FieldType.Keyword)
    private String programAreaCd;
    @Field(name = "jurisdiction_cd", type = FieldType.Long)
    private Long jurisdictionCd;
    @Field(name = "pregnant_ind_cd", type = FieldType.Keyword)
    private String pregnantIndCd;
    @Field(name = "local_id", type = FieldType.Keyword)
    private String localId;
    @Field(name = "activity_to_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant activityToTime;
    @Field(name = "effective_from_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant effectiveFromTime;
    @Field(name = "rpt_to_state_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant rptToStateTime;
    @Field(name = "add_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant addTime;
    @Field(name = "electronic_ind", type = FieldType.Keyword)
    private String electronicInd;
    @Field(name = "version_ctrl_nbr", type = FieldType.Integer)
    private Long versionCtrlNbr;
    @Field(name = "type_cd", type = FieldType.Keyword)
    private String typeCd;
    @Field(name = "observation_record_status_cd", type = FieldType.Keyword)
    private String observationRecordStatusCd;
    @Field(name = "observation_uid", type = FieldType.Keyword)
    private String observationUid;
    @Field(name = "add_user_id", type = FieldType.Long)
    private Long addUserId;
    @Field(name = "last_chg_user_id", type = FieldType.Long)
    private Long lastChgUserId;
    @Field(name = "subject_class_cd", type = FieldType.Keyword)
    private String subjectClassCd;
    @Field(name = "subject_entity_uid", type = FieldType.Long)
    private Long subjectEntityUid;
    @Field(name = "act_uid", type = FieldType.Long)
    private Long actUid;
    @Field(name = "person_cd", type = FieldType.Keyword)
    private String personCd;
    @Field(name = "person_record_status_cd", type = FieldType.Keyword)
    private String personRecordStatusCd;
    @Field(name = "cd_desc_txt", type = FieldType.Text)
    private String cdDescTxt;
    @Field(name = "display_name", type = FieldType.Text)
    private String displayName;
    @Field(name = "participation_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant participationLastChgTime;
    @Field(name = "act_id_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant actIdLastChgTime;
    @Field(name = "person_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant personLastChgTime;
    @Field(name = "observation_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant observationLastChgTime;
}
