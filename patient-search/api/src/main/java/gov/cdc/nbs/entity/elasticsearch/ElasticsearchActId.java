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
public class ElasticsearchActId {
    public static final String ID = "id";
    public static final String ACT_ID_SEQ = "act_id_seq";
    public static final String RECORD_STATUS = "record_status";
    public static final String ROOT_EXTENSION_TXT = "root_extension_txt";
    public static final String TYPE_CD = "type_cd";
    public static final String TYPE_DESC_TXT = "type_desc_txt";
    public static final String LAST_CHANGE_TIME = "last_change_time";

    @Field(name = ID, type = FieldType.Long)
    private Long id;

    @Field(name = ACT_ID_SEQ, type = FieldType.Integer)
    private Integer actIdSeq;

    @Field(name = RECORD_STATUS, type = FieldType.Keyword)
    private String recordStatus;

    @Field(name = ROOT_EXTENSION_TXT, type = FieldType.Keyword)
    private String rootExtensionTxt;

    @Field(name = TYPE_CD, type = FieldType.Keyword)
    private String typeCd;

    @Field(name = TYPE_CD, type = FieldType.Text)
    private String typeDescTxt;

    @Field(name = LAST_CHANGE_TIME, type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(InstantConverter.class)
    private Instant lastChangeTime;
}
