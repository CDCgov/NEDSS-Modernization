package gov.cdc.nbs.entity.elasticsearch;

import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import javax.persistence.Id;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

@Data
@Builder
@Document(indexName = "lab_report")
public class LabReport {

  public static final String CLASS_CD = "class_cd";
  public static final String MOOD_CD = "mood_cd";
  public static final String OBSERVATION_LAST_CHG_TIME = "observation_last_chg_time";
  public static final String RECORD_STATUS_CD = "record_status_cd";
  public static final String PROGRAM_AREA_CD = "program_area_cd";
  public static final String JURISDICTION_CD = "jurisdiction_cd";
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
  public static final String OBSERVATIONS_FIELD = "observations";
  public static final String ACT_IDS = "act_ids";

  @Id
  private String id;

  @Field(name = "observation_uid", type = FieldType.Long)
  private Long observationUid;

  @Field(name = "last_change_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant lastChange;

  @Field(name = "class_cd", type = FieldType.Keyword)
  private String classCd;

  @Field(name = "mood_cd", type = FieldType.Keyword)
  private String moodCd;

  @Field(name = "observation_last_chg_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant observationLastChgTime;

  @Field(name = "cd_desc_txt", type = FieldType.Text)
  private String cdDescTxt;

  @Field(name = "record_status_cd", type = FieldType.Keyword)
  private String recordStatusCd;

  @Field(name = "program_jurisdiction_oid", type = FieldType.Long)
  private Long programJurisdictionOid;

  @Field(name = "program_area_cd", type = FieldType.Keyword)
  private String programAreaCd;

  @Field(name = "jurisdiction_cd", type = FieldType.Long)
  private Long jurisdictionCd;

  @Field(name = "jurisdiction_code_desc_txt", type = FieldType.Text)
  private String jurisdictionCodeDescTxt;

  @Field(name = "pregnant_ind_cd", type = FieldType.Keyword)
  private String pregnantIndCd;

  @Field(name = "local_id", type = FieldType.Keyword)
  private String localId;

  @Field(name = "activity_to_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant activityToTime;

  @Field(name = "effective_from_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant effectiveFromTime;

  @Field(name = "rpt_to_state_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant rptToStateTime;

  @Field(name = "add_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant addTime;

  @Field(name = "electronic_ind", type = FieldType.Keyword)
  private String electronicInd;

  @Field(name = "version_ctrl_nbr", type = FieldType.Integer)
  private Long versionCtrlNbr;

  @Field(name = "add_user_id", type = FieldType.Long)
  private Long addUserId;

  @Field(name = "last_chg_user_id", type = FieldType.Long)
  private Long lastChgUserId;

  // nested fields
  @Field(name = "person_participations", type = FieldType.Nested)
  private List<ElasticsearchPersonParticipation> personParticipations;

  @Field(name = "organization_participations", type = FieldType.Nested)
  private List<ElasticsearchOrganizationParticipation> organizationParticipations;

  @Field(name = "material_participations", type = FieldType.Nested)
  private List<ElasticsearchMaterialParticipation> materialParticipations;

  @Field(name = "observations", type = FieldType.Nested)
  private List<ElasticsearchObservation> observations;

  @Field(name = "act_ids", type = FieldType.Nested)
  private List<ElasticsearchActId> actIds;

  @Field(name = "associated_investigations", type = FieldType.Nested)
  private List<AssociatedInvestigation> associatedInvestigations;

  public List<ElasticsearchPersonParticipation> getPersonParticipations() {
    return personParticipations == null
        ? Collections.emptyList()
        : personParticipations;
  }

  public List<ElasticsearchOrganizationParticipation> getOrganizationParticipations() {
    return organizationParticipations == null
        ? Collections.emptyList()
        : organizationParticipations;
  }

  public List<ElasticsearchObservation> getObservations() {
    return observations == null
        ? Collections.emptyList()
        : observations;
  }

  public List<AssociatedInvestigation> getAssociatedInvestigations() {
    return associatedInvestigations == null
        ? Collections.emptyList()
        : associatedInvestigations;
  }
}
