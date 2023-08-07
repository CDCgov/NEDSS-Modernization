package gov.cdc.nbs.questionbank.condition.request;

import lombok.Data;

@Data
public class ReadConditionRequest {

    private String id;

    private String conditionShortNm;

    private String progAreaCd;

    private String familyCd;

    private String coinfectionGrpCd;

    private Character nndInd;

    private String investigationFormCd;

    private Character statusCd;

}
