package gov.cdc.nbs.questionbank.condition.request;

import java.time.Instant;
import gov.cdc.nbs.questionbank.entity.condition.CondtionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public sealed interface CreateConditionRequest {

    CodeSystem codeSystemCd();

    String conditionCd();

    String conditionShortNm();

    String progAreaCd();

    String familyCd();

    String coinfectionGrpCd();

    String nndInd();

    String reportableMorbidityInd();

    String reportableSummaryInd();

    String contactTracingEnableInd();

    //All UI fields for condition
    record Text(
        CodeSystem codeSystemCd,
        String conditionCd,
        String conditionShortNm,
        String progAreaCd,
        String familyCd,
        String coinfectionGrpCd,
        String nndInd,
        String reportableMorbidInd,
        String reportableSummaryInd,
        String contactTracingEnableInd
    )

    record CodeSystem(
        String assigningAuthorityCd,
        String assigningAuthorityDescTxt,
        String codeSystemCd,
        String codeSystemDescTxt
    ) implements CreateConditionRequest {

    }
}
