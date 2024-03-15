package gov.cdc.nbs.entity.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElasticsearchObservation {

    public static final String CD_DESC_TXT = "cd_desc_txt";
    public static final String DISPLAY_NAME = "display_name";

    @Field(name = "cd", type = FieldType.Keyword)
    private String cd;

    @Field(name = "cd_desc_txt", type = FieldType.Keyword)
    private String cdDescTxt;

    @Field(name = "domain_cd_st_1", type = FieldType.Keyword)
    private String domainCd;

    @Field(name = "status_cd", type = FieldType.Keyword)
    private String statusCd;

    @Field(name = "alt_cd", type = FieldType.Keyword)
    private String altCd;

    @Field(name = "alt_cd_desc_txt", type = FieldType.Keyword)
    private String altDescTxt;

    @Field(name = "alt_cd_system_cd", type = FieldType.Keyword)
    private String altCdSystemCd;

    @Field(name = "display_name", type = FieldType.Keyword)
    private String displayName;

    @Field(name = "ovc_code", type = FieldType.Keyword)
    private String ovcCode;

    @Field(name = "ovc_alt_cd", type = FieldType.Keyword)
    private String ovcAltCode;

    @Field(name = "ovc_alt_cd_desc_txt", type = FieldType.Keyword)
    private String ovcAltDescTxt;

    @Field(name = "ovc_alt_cd_system_cd", type = FieldType.Keyword)
    private String ovcAltCdSystemCd;

}
