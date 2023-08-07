package gov.cdc.nbs.questionbank.condition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.ReadConditionResponse;

@Service
public class ConditionReader {
    @Autowired
    private ConditionCodeRepository conditionCodeRepository;

    public Page<ReadConditionResponse.GetCondition> findConditions(Pageable pageable){
        Page<ConditionCode> result = conditionCodeRepository.findAll(pageable);
        List<ReadConditionResponse.GetCondition> resultToList = readCondition(result);
        return new PageImpl<>(resultToList, pageable, result.getTotalElements());
    }

    public Page<ReadConditionResponse.GetCondition> searchCondition(ReadConditionRequest search, Pageable pageable) {
        Page<ConditionCode> result = conditionCodeRepository.findByField(search.getId(), search.getConditionShortNm(),
                search.getProgAreaCd(), search.getFamilyCd(), search.getCoinfectionGrpCd(),search.getNndInd(), search.getInvestigationFormCd(), search.getStatusCd(), pageable);
        List<ReadConditionResponse.GetCondition> resultToList = readCondition(result);
        return new PageImpl<>(resultToList, pageable, result.getTotalElements());
    }

    public List<ReadConditionResponse.GetCondition> readCondition(Page<ConditionCode> result) {
        List<ReadConditionResponse.GetCondition> results = new ArrayList<>();
        for (ConditionCode conditionCode : result.getContent()) {
            results.add(new ReadConditionResponse.GetCondition(conditionCode.getId(), conditionCode.getConditionShortNm(),
                    conditionCode.getProgAreaCd(), conditionCode.getFamilyCd(), conditionCode.getCoinfectionGrpCd(),
                    conditionCode.getNndInd(), conditionCode.getInvestigationFormCd(), conditionCode.getStatusCd()));
        }
        return results;
    }
}
