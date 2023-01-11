package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.config.ElasticSearchConfig.DATE_PATTERN;

import java.time.Instant;
import java.util.List;

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

    public static final String OBSERVATION_UID = "observation_uid";
    public static final String LAST_CHANGE_TIME = "last_change_time";
    public static final String CLASS_CD = "class_cd";
    public static final String MOOD_CD = "mood_cd";
    public static final String OBSERVATION_LAST_CHG_TIME = "observation_last_chg_time";
    public static final String CD_DESC_TXT = "cd_desc_txt";
    public static final String RECORD_STATUS_CD = "record_status_cd";
    public static final String PROGRAM_JURISDICTION_OID = "program_jurisdiction_oid";
    public static final String PROGRAM_AREA_CD = "program_area_cd";
    public static final String JURISDICTION_CD = "jurisdiction_cd";
    public static final String JURISDICTION_CODE_DESC_TXT = "jurisdiction_code_desc_txt";
    public static final String PREGNANT_IND_CD = "pregnant_ind_cd";
    public static final String LOCAL_ID = "local_id";
    public static final String ACTIVITY_TO_TIME = "activity_to_time";
    public static final String EFFECTIVE_FROM_TIME = "effective_from_time";
    public static final String RPT_TO_STATE_TIME = "rpt_to_state_time";
    public static final String ADD_TIME = "add_time";
    public static final String ELECTRONIC_IND = "electronic_ind";
    public static final String VERSION_CTRL_NBR = "version_ctrl_nbr";
    public static final String ADD_USER_ID = "add_user_id";
    public static final String LAST_CHG_USER_ID = "last_chg_user_id";
    public static final String PERSON_PARTICIPATIONS = "person_participations";
    public static final String ORGANIZATION_PARTICIPATIONS = "organization_participations";
    public static final String MATERIAL_PARTICIPATIONS = "material_participations";
    public static final String OBSERVATIONS = "observations";
    public static final String ACT_IDS = "act_ids";
    public static final String ASSOCIATED_INVESTIGATIONS = "associated_investigations";

    /*
     * Same as observation_uid
     */
    @Id
    private String id;

    @Field(name = OBSERVATION_UID, type = FieldType.Long)
    private Long observationUid;

    @Field(name = LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant lastChange;

    @Field(name = CLASS_CD, type = FieldType.Keyword)
    private String classCd;

    @Field(name = MOOD_CD, type = FieldType.Keyword)
    private String moodCd;

    @Field(name = OBSERVATION_LAST_CHG_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant observationLastChgTime;

    @Field(name = CD_DESC_TXT, type = FieldType.Text)
    private String cdDescTxt;

    @Field(name = RECORD_STATUS_CD, type = FieldType.Keyword)
    private String recordStatusCd;

    @Field(name = PROGRAM_JURISDICTION_OID, type = FieldType.Long)
    private Long programJurisdictionOid;

    @Field(name = PROGRAM_AREA_CD, type = FieldType.Keyword)
    private String programAreaCd;

    @Field(name = JURISDICTION_CD, type = FieldType.Long)
    private Long jurisdictionCd;

    @Field(name = JURISDICTION_CODE_DESC_TXT, type = FieldType.Text)
    private String jurisdictionCodeDescTxt;

    @Field(name = PREGNANT_IND_CD, type = FieldType.Keyword)
    private String pregnantIndCd;

    @Field(name = LOCAL_ID, type = FieldType.Keyword)
    private String localId;

    @Field(name = ACTIVITY_TO_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant activityToTime;

    @Field(name = EFFECTIVE_FROM_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant effectiveFromTime;

    @Field(name = RPT_TO_STATE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant rptToStateTime;

    @Field(name = ADD_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant addTime;

    @Field(name = ELECTRONIC_IND, type = FieldType.Keyword)
    private String electronicInd;

    @Field(name = VERSION_CTRL_NBR, type = FieldType.Integer)
    private Long versionCtrlNbr;

    @Field(name = ADD_USER_ID, type = FieldType.Long)
    private Long addUserId;

    @Field(name = LAST_CHG_USER_ID, type = FieldType.Long)
    private Long lastChgUserId;

    // nested fields

    @Field(name = PERSON_PARTICIPATIONS, type = FieldType.Nested)
    private List<ElasticsearchPersonParticipation> personParticipations;

    @Field(name = ORGANIZATION_PARTICIPATIONS, type = FieldType.Nested)
    private List<ElasticsearchOrganizationParticipation> organizationParticipations;

    @Field(name = MATERIAL_PARTICIPATIONS, type = FieldType.Nested)
    private List<ElasticsearchMaterialParticipation> materialParticipations;

    @Field(name = OBSERVATIONS, type = FieldType.Nested)
    private List<ElasticsearchObservation> observations;

    @Field(name = ACT_IDS, type = FieldType.Nested)
    private List<ElasticsearchActId> actIds;

    @Field(name = ASSOCIATED_INVESTIGATIONS, type = FieldType.Nested)
    private List<AssociatedInvestigation> associatedInvestigations;
}
