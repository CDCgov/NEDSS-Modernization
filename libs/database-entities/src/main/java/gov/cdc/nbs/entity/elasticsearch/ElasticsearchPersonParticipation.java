package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

import java.time.Instant;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElasticsearchPersonParticipation {
    public static final String ACT_UID = "act_uid";
    public static final String TYPE_CD = "type_cd";
    public static final String ENTITY_ID = "entity_id";
    public static final String LOCAL_ID = "local_id";
    public static final String SUBJECT_CLASS_CD = "subject_class_cd";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String BIRTH_TIME = "birth_time";
    public static final String CURR_SEX_CD = "curr_sex_cd";
    public static final String PERSON_LAST_CHANGE_TIME = "person_last_change_time";
    public static final String PERSON_CD = "person_cd";
    public static final String PERSON_PARENT_UID = "person_parent_uid";
    public static final String PERSON_RECORD_STATUS = "person_record_status";
    public static final String PARTICIPATION_LAST_CHANGE_TIME = "participation_last_change_time";
    public static final String PARTICIPATION_RECORD_STATUS = "participation_record_status";
    public static final String PARTICIPATION_TYPE_DESC_TXT = "type_desc_txt";

    @Field(name = ACT_UID, type = FieldType.Long)
    private Long actUid;

    @Field(name = TYPE_CD, type = FieldType.Keyword)
    private String typeCd;

    @Field(name = ENTITY_ID, type = FieldType.Long)
    private Long entityId;

    @Field(name = LOCAL_ID, type = FieldType.Keyword)
    private String localId;

    @Field(name = SUBJECT_CLASS_CD, type = FieldType.Keyword)
    private String subjectClassCd;

    @Field(name = PARTICIPATION_RECORD_STATUS, type = FieldType.Keyword)
    private String participationRecordStatus;

    @Field(name = PARTICIPATION_TYPE_DESC_TXT, type = FieldType.Keyword)
    private String typeDescTxt;

    @Field(name = PARTICIPATION_LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant participationLastChangeTime;

    @Field(name = FIRST_NAME, type = FieldType.Text)
    private String firstName;

    // allows sorting
    @MultiField(mainField = @Field(name = LAST_NAME, type = FieldType.Text), otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword, normalizer = "lowercase")
    })
    private String lastName;

    @Field(name = BIRTH_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant birthTime;

    @Field(name = CURR_SEX_CD, type = FieldType.Keyword)
    private String currSexCd;

    @Field(name = PERSON_CD, type = FieldType.Keyword)
    private String personCd;

    @Field(name = PERSON_PARENT_UID, type = FieldType.Long)
    private Long personParentUid;

    @Field(name = PERSON_RECORD_STATUS, type = FieldType.Keyword)
    private String personRecordStatus;

    @Field(name = PERSON_LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant personLastChangeTime;

}
