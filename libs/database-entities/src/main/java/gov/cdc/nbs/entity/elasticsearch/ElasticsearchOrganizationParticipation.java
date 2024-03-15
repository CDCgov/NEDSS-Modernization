package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

import java.time.Instant;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElasticsearchOrganizationParticipation {
    public static final String TYPE_CD = "type_cd";
    public static final String ENTITY_ID = "entity_id";
    public static final String SUBJECT_CLASS_CD = "subject_class_cd";

    @Field(name = "act_uid", type = FieldType.Long)
    private Long actUid;

    @Field(name = "type_cd", type = FieldType.Keyword)
    private String typeCd;

    @Field(name = "entity_id", type = FieldType.Long)
    private Long entityId;

    @Field(name = "subject_class_cd", type = FieldType.Keyword)
    private String subjectClassCd;

    @Field(name = "participation_record_status", type = FieldType.Keyword)
    private String participationRecordStatus;

    @Field(name = "type_desc_txt", type = FieldType.Keyword)
    private String typeDescTxt;

    @Field(name = "participation_last_change_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant participationLastChangeTime;

    @Field(name = "name", type = FieldType.Text)
    private String name;

    @Field(name = "organization_last_change_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant organizationLastChangeTime;

}
