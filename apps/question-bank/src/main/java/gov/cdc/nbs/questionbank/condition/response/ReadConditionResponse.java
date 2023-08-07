package gov.cdc.nbs.questionbank.condition.response;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReadConditionResponse {
    public record GetCondition (

            String id,

            String conditionShortNm,

            String progAreaCd,

            String familyCd,

            String coinfectionGrpCd,

            Character nndInd,

            String investigationFormCd,

            Character statusCd

    ) {}
}
