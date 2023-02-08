package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

import java.time.Instant;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElasticsearchMaterialParticipation {
    public static final String ACT_UID = "act_uid";
    public static final String TYPE_CD = "type_cd";
    public static final String ENTITY_ID = "entity_id";
    public static final String SUBJECT_CLASS_CD = "subject_class_cd";
    public static final String PARTICIPATION_LAST_CHANGE_TIME = "participation_last_change_time";
    public static final String PARTICIPATION_RECORD_STATUS = "participation_record_status";
    public static final String MATERIAL_CD = "material_cd";
    public static final String MATERIAL_CD_DESC_TXT = "material_cd_desc_txt";
    public static final String PARTICIPATION_TYPE_DESC_TXT = "type_desc_txt";

    @Field(name = ACT_UID, type = FieldType.Long)
    private Long actUid;

    @Field(name = TYPE_CD, type = FieldType.Keyword)
    private String typeCd;

    @Field(name = ENTITY_ID, type = FieldType.Keyword)
    private String entityId;

    @Field(name = SUBJECT_CLASS_CD, type = FieldType.Keyword)
    private String subjectClassCd;

    @Field(name = PARTICIPATION_RECORD_STATUS, type = FieldType.Keyword)
    private String participationRecordStatus;

    @Field(name = PARTICIPATION_TYPE_DESC_TXT, type = FieldType.Keyword)
    private String typeDescTxt;

    @Field(name = PARTICIPATION_LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant participationLastChangeTime;

    @Field(name = MATERIAL_CD, type = FieldType.Text)
    private String cd;

    @Field(name = MATERIAL_CD_DESC_TXT, type = FieldType.Text)
    private String cdDescTxt;

}
