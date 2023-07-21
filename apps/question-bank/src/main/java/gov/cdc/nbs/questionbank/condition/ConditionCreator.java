package gov.cdc.nbs.questionbank.condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConditionCreator {

    private static final String defaultCodingSys = "2.16.840.1.114222.4.5.277";

    @Autowired
    private ConditionCodeRepository conditionCodeRepository;

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
            //additional default values
            conditionCode.setVaccineEnableInd('N');//ND-19671
            conditionCode.setTreatmentEnableInd('Y');
            conditionCode.setLabReportEnableInd('N');//ND-19671
            conditionCode.setMorbReportEnableInd('Y');
            conditionCode.setStatusCd('A');
            conditionCode.setConditionDescTxt(request.getConditionShortNm());

            ConditionCode result = conditionCodeRepository.save(conditionCode);

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
