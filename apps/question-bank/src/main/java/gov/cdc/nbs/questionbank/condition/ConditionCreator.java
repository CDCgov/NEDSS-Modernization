package gov.cdc.nbs.questionbank.condition;

import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.entity.condition.LdfPageSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ConditionCreator {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Instant instant = timestamp.toInstant();

    @Autowired
    private ConditionCodeRepository conditionCodeRepository;

    @Autowired
    private LdfPageSetRepository ldfPageSetRepository;

    public CreateConditionResponse createCondition(CreateConditionRequest request, long userId) {
        CreateConditionResponse response = new CreateConditionResponse();
        String id = request.getId();
        String conditionNm = request.getConditionShortNm();

        //check if id already exists
        if(checkId(id)) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            return response;
        }

        //check if conditionNm already exists
        if (checkConditionNm(conditionNm)) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            return response;
        }

        try {
            ConditionCode conditionCode = new ConditionCode(conditionAdd(request, userId));
            //setting default values for create condition

            //setIndentLevel
            String parentIsCd = conditionCode.getParentIsCd();
            if (parentIsCd == null) {
                conditionCode.setIndentLevelNbr((short) 1);
            } else {
                conditionCode.setIndentLevelNbr((short) 2); //This scenario should not happen
            }
            //codeSetNm
            String codeSetNm = conditionCode.getConditionCodesetNm()== null ? "PHC_TYPE" : conditionCode.getConditionCodesetNm();
            conditionCode.setConditionCodesetNm(codeSetNm);
            //conditionSeqNm
            Short conditionSeqNm = conditionCode.getConditionSeqNum()== null ? 1 : conditionCode.getConditionSeqNum();
            conditionCode.setConditionSeqNum(conditionSeqNm);
            //assigning authority cd/txt
            String assigningAuthorityCd = conditionCode.getAssigningAuthorityCd()== null ? "2.16.840.1.114222" : conditionCode.getAssigningAuthorityCd();
            conditionCode.setAssigningAuthorityCd(assigningAuthorityCd);
            String assigningAuthorityDescTxt = conditionCode.getAssigningAuthorityDescTxt()== null ? "Centers for Disease Control" : conditionCode.getAssigningAuthorityDescTxt();
            conditionCode.setAssigningAuthorityDescTxt(assigningAuthorityDescTxt);

            //code system
            String codeSystemTxt = conditionCode.getCodeSystemDescTxt();
            if (codeSystemTxt.equals("Local")) {
                conditionCode.setCodeSystemCd("L");
            } else if (codeSystemTxt.equals("SNOMED-CT")) {
                conditionCode.setCodeSystemCd("2.16.840.1.113883.6.96");
            } else{
                conditionCode.setCodeSystemCd("2.16.840.1.114222.4.5.277");
            }
            //radio buttons
            conditionCode.setNndInd('Y');
            conditionCode.setReportableMorbidityInd('Y');
            conditionCode.setReportableSummaryInd('N');
            conditionCode.setContactTracingEnableInd('Y');
            conditionCode.setIsModifiableInd('N');
            //additional default values
            conditionCode.setEffectiveFromTime(instant);
            conditionCode.setStatusTime(instant);
            conditionCode.setVaccineEnableInd('N');//ND-19671
            conditionCode.setTreatmentEnableInd('Y');
            conditionCode.setLabReportEnableInd('N');//ND-19671
            conditionCode.setMorbReportEnableInd('Y');
            conditionCode.setStatusCd('A');
            conditionCode.setPortReqIndCd('F');
            conditionCode.setConditionDescTxt(request.getConditionShortNm());

            ConditionCode result = conditionCodeRepository.save(conditionCode);
            //ldf
            LdfPageSet ldf = new LdfPageSet();
            ldf.setId(getLdfId());
            ldf.setBusinessObjectNm("PHC");
            ldf.setConditionCd(conditionCode);
            ldf.setUiDisplay("Link");
            ldf.setIndentLevelNbr((short) 2);
            ldf.setParentIsCd("30");
            ldf.setCodeSetNm("LDF_PAGE_SET");
            ldf.setSeqNum((short) 1);
            ldf.setCodeVersion(String.valueOf(1));
            ldf.setEffectiveFromTime(conditionCode.getEffectiveFromTime());
            ldf.setEffectiveToTime(conditionCode.getEffectiveToTime());
            ldf.setNbsUid(ldfPageSetRepository.findNbsUid());
            ldf.setStatusCd(conditionCode.getStatusCd());
            ldf.setCodeShortDescTxt(conditionCode.getConditionDescTxt());
            ldf.setDisplayRow(findDisplayRow());
            ldf.setDisplayColumn((short) 2);
            LdfPageSet ldfAdd = ldfPageSetRepository.save(ldf);
            result.setLdf(ldfAdd);
            conditionCodeRepository.save(result);

            response.setId(result.getId());
            response.setStatus(HttpStatus.CREATED);

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;


    }

    public boolean checkId(String id) {
        return (id != null && conditionCodeRepository.checkId(id) > 0);
    }

    public boolean checkConditionNm(String conditionNm) {
        return (conditionNm != null && conditionCodeRepository.checkConditionName(conditionNm) > 0);
    }

    public short findDisplayRow() {
        return (short) (ldfPageSetRepository.findMaxDisplayRow() + 1);
    }

    public String getLdfId() {
        List<String> allIds = ldfPageSetRepository.findAllIds();
        String currentYear = String.valueOf(LocalDate.now().getYear());
        int numericPart = 0;

        if (!allIds.isEmpty()) {
            numericPart = allIds.stream()
                    .filter(id -> id.startsWith(currentYear))
                    .mapToInt(id -> Integer.parseInt(id.substring(4)))
                    .max()
                    .orElse(0);
        }
        numericPart++;
        return currentYear + String.format("%03d", numericPart);
    }

    public ConditionCommand.AddCondition conditionAdd(final CreateConditionRequest request, long userId) {
        return new ConditionCommand.AddCondition(
                request.getId(),
                request.getCodeSystemDescTxt(),
                request.getConditionShortNm(),
                request.getNndInd(),
                request.getProgAreaCd(),
                request.getReportableMorbidityInd(),
                request.getReportableSummaryInd(),
                request.getContactTracingEnableInd(),
                request.getFamilyCd(),
                request.getCoinfectionGrpCd(),
                userId);
    }

}
