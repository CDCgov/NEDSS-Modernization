package gov.cdc.nbs.questionbank.condition;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

@Service
public class ConditionCreator {
    private ConditionCodeRepository conditionCodeRepository;


    public CreateConditionResponse createConditionResponse(CreateConditionRequest request, long userId){
        CreateConditionResponse response = new CreateConditionResponse();

        //first check if condition exists - if it does don't create it

        if (checkConditionExists(request.getConditionCodesetNm())) {
            response.setMessage('Condition already exists');
            response.setStatus(HttpStatus.BAD_REQUEST);
            return response;
        }

        //logic in legacy code can be found in PortPageUtil.java

        if(userId == null) {
            try {
                ConditionCode condition = new ConditionCode(conditionAdd(request, userId));
                ConditionCode id = new ConditionCodeId();
                id.setConditionCodesetNm(request.getConditionCodesetNm());
                condition.setId(id);
                ConditionCode saveCondition = conditionCodeRepository.save(condition);
                
                CreateConditionResponse.CreateConditionBody condition = new CreateConditionResponse.CreateConditionBody(
                    saveCondition.getId(),
                    saveCondition.getConditionCodesetNm()   

                );
                response.setBody(condition);
                response.setMessage('Condition created');
                response.setStatus(HttpStatus.CREATED);
            } catch (Exception e) {
                response.setMessage(e.getMessage());
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return response;
        }


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
