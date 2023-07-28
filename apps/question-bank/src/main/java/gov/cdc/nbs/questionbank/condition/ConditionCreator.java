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

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ConditionCreator {

    private static final String defaultCodingSys = "2.16.840.1.114222.4.5.277";
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
            //radio buttons
            conditionCode.setNndInd('Y');
            conditionCode.setReportableMorbidityInd('Y');
            conditionCode.setReportableSummaryInd('N');
            conditionCode.setContactTracingEnableInd('Y');
            conditionCode.setCodeSystemCd(defaultCodingSys);
            conditionCode.setIsModifiableInd('N');
            //additional default values
            conditionCode.setConditionCodesetNm("PHC_TYPE");
            conditionCode.setConditionSeqNum((short) 1);
            conditionCode.setEffectiveFromTime(instant);
            conditionCode.setStatusTime(instant);
            conditionCode.setVaccineEnableInd('N');//ND-19671
            conditionCode.setTreatmentEnableInd('Y');
            conditionCode.setLabReportEnableInd('N');//ND-19671
            conditionCode.setMorbReportEnableInd('Y');
            conditionCode.setStatusCd('A');
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
            ldf.setNbsUid(Math.toIntExact(conditionCode.getNbsUid()));
            ldf.setEffectiveFromTime(conditionCode.getEffectiveFromTime());
            ldf.setEffectiveToTime(conditionCode.getEffectiveToTime());
            ldf.setStatusCd(conditionCode.getStatusCd());
            ldf.setCodeShortDescTxt(conditionCode.getConditionDescTxt());
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

    public String findMaxIdNumerically(){
        List<String> allIds = ldfPageSetRepository.findAllIds();
        int maxNumericId = -1;

        for(String id: allIds) {
            int numericId = Integer.parseInt(id);
            maxNumericId = Math.max(maxNumericId, numericId);
        }

        if(maxNumericId == -1){
            return "No numeric IDs found";
        } else {
            return String.valueOf(maxNumericId);
        }
    }

    //priority queues, stack
    public String getLdfId(){
        String maxNumericId = findMaxIdNumerically();
        if (!maxNumericId.equals("No numeric IDs found")) {
            int nextIdNumeric = Integer.parseInt(maxNumericId) + 1;
            return String.valueOf(nextIdNumeric);
        }
        return "1";
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
