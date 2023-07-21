package gov.cdc.nbs.questionbank.condition.request;

import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateConditionRequest {

    private String id;

    private String codeSystemDescTxt;

    private String conditionShortNm;

    private String progAreaCd;

    private Character nndInd;

    private Character reportableMorbidityInd;

    private Character reportableSummaryInd;

    private Character contactTracingEnableInd;

    private String familyCd;

    private String coinfectionGrpCd;

}
