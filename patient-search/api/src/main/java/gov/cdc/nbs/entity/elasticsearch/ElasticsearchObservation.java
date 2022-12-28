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

    public static final String CD = "cd";
    public static final String CD_DESC_TXT = "cd_desc_txt";
    public static final String DOMAIN_CD = "obs_domain_cd_st_1";
    public static final String STATUS_CD = "status_cd";
    public static final String ALT_CD = "alt_cd";
    public static final String ALT_DESC_TXT = "alt_cd_desc_txt";
    public static final String ALT_CD_SYSTEM_CD = "alt_cd_system_cd";
    public static final String DISPLAY_NAME = "display_name";
    public static final String OVC_CODE = "ovc_code";
    public static final String OVC_ALT_CODE = "ovc_alt_cd";
    public static final String OVC_ALT_DESC_TXT = "ovc_alt_cd_desc_txt";
    public static final String OVC_ALT_CD_SYSTEM_CD = "ovc_alt_cd_system_cd";

    @Field(name = CD, type = FieldType.Keyword)
    private String cd;

    @Field(name = CD_DESC_TXT, type = FieldType.Keyword)
    private String cdDescTxt;

    @Field(name = DOMAIN_CD, type = FieldType.Keyword)
    private String domainCd;

    @Field(name = STATUS_CD, type = FieldType.Keyword)
    private String statusCd;

    @Field(name = ALT_CD, type = FieldType.Keyword)
    private String altCd;

    @Field(name = ALT_DESC_TXT, type = FieldType.Keyword)
    private String altDescTxt;

    @Field(name = ALT_CD_SYSTEM_CD, type = FieldType.Keyword)
    private String altCdSystemCd;

    @Field(name = DISPLAY_NAME, type = FieldType.Keyword)
    private String displayName;

    @Field(name = OVC_CODE, type = FieldType.Keyword)
    private String ovcCode;

    @Field(name = OVC_ALT_CODE, type = FieldType.Keyword)
    private String ovcAltCode;

    @Field(name = OVC_ALT_DESC_TXT, type = FieldType.Keyword)
    private String ovcAltDescTxt;

    @Field(name = OVC_ALT_CD_SYSTEM_CD, type = FieldType.Keyword)
    private String ovcAltCdSystemCd;

}
