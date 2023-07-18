package gov.cdc.nbs.questionbank.condition.request;

import java.time.Instant;
import gov.cdc.nbs.questionbank.entity.condition.CondtionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateConditionRequest {

    private String conditionCd;

    private String conditionCodesetNm;

    private Integer conditionSeqNm;

    private String assigningAuthorityCd;

    private String assigningAuthorityDescText;

    private String codeSystemCd;

    private String codeSystemDescTxt;

    private String conditionDescTxt;

    private String conditionShortNm;

    private Instant effectiveFromTime;

    private Instant effectiveToTime;

    private Integer indentLevelNbr;

    private String investigationFormCd;

    private String isModifiableInd;

    private Integer nbsUid;

    private String nndInd;

    private String parentIsCd;

    private String progAreaCd;

    private String reportableMorbidityInd;

    private String reportableSummaryInd;

    private String statusCd;

    private Instant statusTime;

    private String nndEntityIdentifier;

    private String nndSummaryEntityIdentifier;

    private String summaryInvestigationFormCd;

    private String contactTracingEnable;

    private String vaccineEnable;

    private String treatmentEnable;

    private String labReportEnable;

    private String morbReportEnable;

    private String portReqIndCd;

    private String familyCd;

    private String coinfectionGrpCd;

    private String rhapParseNbsInd;

    private String rhapActionValue;
}
