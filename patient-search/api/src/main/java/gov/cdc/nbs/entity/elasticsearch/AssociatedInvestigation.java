package gov.cdc.nbs.entity.elasticsearch;

import static gov.cdc.nbs.config.ElasticSearchConfig.DATE_PATTERN;

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
public class AssociatedInvestigation {
    public static final String PUBLIC_HEALTH_CASE_UID = "public_health_case_uid";
    public static final String LAST_CHANGE_TIME = "last_change_time";
    public static final String CD_DESC_TXT = "cd_desc_txt";
    public static final String LOCAL_ID = "local_id";
    public static final String ACT_RELATIONSHIP_LAST_CHANGE_TIME = "act_relationship_last_change_time";

    @Field(name = PUBLIC_HEALTH_CASE_UID, type = FieldType.Long)
    private Long publicHealthCaseUid;

    @Field(name = CD_DESC_TXT, type = FieldType.Keyword)
    private String cdDescTxt;

    @Field(name = LOCAL_ID, type = FieldType.Keyword)
    private String localId;

    @Field(name = LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant lastChgTime;

    @Field(name = ACT_RELATIONSHIP_LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant actRelationshipLastChgTime;

}
