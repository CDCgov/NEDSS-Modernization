package gov.cdc.nbs.questionbank.condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.condition.repository.ConditionRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConditionCreator {

    private static final String defaultCodingSys = "2.16.840.1.114222.4.5.277";

    @Autowired
    private ConditionRepository conditionCodeRepository;

    public CreateConditionResponse createCondition(CreateConditionRequest request, long userId){
        CreateConditionResponse response = new CreateConditionResponse();
        String conditionNm = request.getConditionShortNm();

        //check if conditionNm already exists
        if(checkConditionNm(conditionNm)){
            response.setStatus(HttpStatus.BAD_REQUEST);
            return response;
        }

        try{
            ConditionCode conditionCode = new ConditionCode(conditionAdd(request, userId));
            //radio buttons
            conditionCode.setNndInd("Y");
			conditionCode.setReportableMorbidityInd("Y");
			conditionCode.setReportableSummaryInd("N");
			conditionCode.setContactTracingEnableInd("Y");
			conditionCode.setCodeSystemCd(defaultCodingSys);
            //additional default values
            conditionCode.setVaccineModuleEnableInd("N");//ND-19671
			conditionCode.setTreatmentModuleEnableInd("Y");
			conditionCode.setLabReportModuleEnableInd("N");//ND-19671
			conditionCode.setMorbReportModuleEnableInd("Y");
			conditionCode.setStatusCd("A");
			conditionCode.setConditionDescTxt(request.getConditionShortNm());

            ConditionCode result = conditionCodeRepository.save(conditionCode);

            CreateConditionResponse.CreateConditionBody body = new CreateConditionResponse.CreateConditionBody(
                result.getConditionCd()
            );

            response.setBody(body);
            response.setStatus(HttpStatus.CREATED);
        
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
        return response;


    }

    public boolean checkConditionNm(String conditionNm){
       return (conditionNm != null && conditionCodeRepository.checkConditionName(conditionNm) > 0);
    }

    public ConditionCommand.AddCondition conditionAdd(final CreateConditionRequest request, long userId) {
        return new CreateConditionCommand.AddCondition(
            request.getConditionCd(),
            request.getConditionCodesetNm(),
            request.getConditionSeqNum(),
            request.getAssigningAuthorityCd(),
            request.getAssigningAuthorityDescTxt(),
            request.getCodeSystemCd(),
            request.getCodeSystemDescTxt(),
            request.getConditionDescTxt(),
            request.getConditionShortNm(),
            request.getEffectiveFromTime(),
            request.getEffectiveToTime(),
            request.getIndentLevelNbr(),
            request.getInvestigationFormCd(),
            request.getIsModifiableInd(),
            request.getNbsUid(),
            request.getNndInd(),
            request.getParentIsCd(),
            request.getProgAreaCd(),
            request.getReportableMorbidityInd(),
            request.getReportableSummaryInd(),
            request.getStatusCd(),
            request.getStatusTime(),
            request.getNndEntityIdentifier(),
            request.getNndSummaryEntityIdentifier(),
            request.getSummaryInvestigationFormCd(),
            request.getContactTracingEnableInd(),
            request.getVaccineEnableInd(),
            request.getTreatmentEnableInd(),
            request.getLabReportEnableInd(),
            request.getMorbReportEnableInd(),
            request.getPortReqIndCd(),
            request.getFamilyCd(),
            request.getCoinfectionGrpCd(),
            request.getRhapParseNbsInd(),
            request.getRhapActionValue(),
            userId
        );
    }
    
}
