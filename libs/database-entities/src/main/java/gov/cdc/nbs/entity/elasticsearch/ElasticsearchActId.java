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
public class ElasticsearchActId {
    public static final String ACT_ID_SEQ = "act_id_seq";
    public static final String ROOT_EXTENSION_TXT = "root_extension_txt";
    public static final String TYPE_CD = "type_cd";
    public static final String TYPE_DESC_TXT = "type_desc_txt";

    @Field(name = "id", type = FieldType.Long)
    private Long id;

    @Field(name = "act_id_seq", type = FieldType.Integer)
    private Integer actIdSeq;

    @Field(name =  "record_status", type = FieldType.Keyword)
    private String recordStatus;

    @Field(name = "root_extension_txt", type = FieldType.Keyword)
    private String rootExtensionTxt;

    @Field(name = "type_cd", type = FieldType.Keyword)
    private String typeCd;

    @Field(name = "type_desc_txt", type = FieldType.Text)
    private String typeDescTxt;

    @Field(name = "last_change_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
    @ValueConverter(ElasticsearchInstantValueConverter.class)
    private Instant lastChangeTime;
}
