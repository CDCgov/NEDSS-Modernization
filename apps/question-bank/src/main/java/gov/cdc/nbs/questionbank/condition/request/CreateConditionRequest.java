package gov.cdc.nbs.questionbank.condition.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateConditionRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String code,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String codeSystemDescTxt,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String conditionShortNm,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String progAreaCd,
    Character nndInd,
    Character reportableMorbidityInd,
    Character reportableSummaryInd,
    Character contactTracingEnableInd,
    String familyCd,
    String coinfectionGrpCd) {}
