package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.config.ElasticSearchConfig.DATE_PATTERN;

import java.time.Instant;

import org.springframework.data.elasticsearch.annotations.Document;
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
@Document(indexName = "participation")
public class ElasticsearchParticipation {
    public static final String ACT_UID = "act_uid";
    public static final String TYPE_CD = "type_cd";
    public static final String ENTITY_ID = "entity_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String BIRTH_TIME = "birth_time";
    public static final String CURR_SEX_CD = "curr_sex_cd";
    public static final String ORGANIZATION_NAME = "organization_name";
    public static final String PERSON_LAST_CHANGE_TIME = "person_last_change_time";
    public static final String ORGANIZATION_LAST_CHANGE_TIME = "org_last_change_time";
    public static final String RECORD_STATUS = "record_status";

    @Field(name = ACT_UID, type = FieldType.Long)
    private Long actUid;

    @Field(name = TYPE_CD, type = FieldType.Keyword)
    private String typeCd;

    @Field(name = RECORD_STATUS, type = FieldType.Keyword)
    private String recordStatus;

    @Field(name = ENTITY_ID, type = FieldType.Long)
    private Long entityId;

    @Field(name = FIRST_NAME, type = FieldType.Text)
    private String firstName;

    @Field(name = LAST_NAME, type = FieldType.Text)
    private String lastName;

    @Field(name = BIRTH_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant birthTime;

    @Field(name = CURR_SEX_CD, type = FieldType.Keyword)
    private String currSexCd;

    @Field(name = ORGANIZATION_NAME, type = FieldType.Text)
    private String organizationName;

    @Field(name = PERSON_LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant personLastChangeTime;

    @Field(name = ORGANIZATION_LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant organizationLastChangeTime;
}
