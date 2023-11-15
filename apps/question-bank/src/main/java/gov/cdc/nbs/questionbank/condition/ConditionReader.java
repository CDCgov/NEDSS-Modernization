package gov.cdc.nbs.questionbank.condition;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.BooleanBuilder;


import com.querydsl.core.types.dsl.BooleanExpression;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;

@Service
public class ConditionReader {
    @Autowired
    private ConditionCodeRepository conditionCodeRepository;

    public Page<Condition> findConditions(Pageable pageable) {
        Page<ConditionCode> result = conditionCodeRepository.findAll(pageable);
        List<Condition> resultToList = readCondition(result);
        return new PageImpl<>(resultToList, pageable, result.getTotalElements());
    }


    public Page<Condition> searchCondition(ReadConditionRequest request, Pageable pageable) {
        Pageable test = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
        if (request.getSearchText() != null && !request.getSearchText().trim().isEmpty()) {
            BooleanBuilder predicate = new BooleanBuilder();
            BooleanExpression searchPredicate = QConditionCode.conditionCode.id.eq(request.getSearchText())
                    .or(QConditionCode.conditionCode.conditionShortNm.containsIgnoreCase(request.getSearchText()));
            predicate.or(searchPredicate);
            Page<ConditionCode> conditionCodePage = conditionCodeRepository.findAll(predicate, pageable);
            List<Condition> resultToList = readCondition(conditionCodePage);
            return new PageImpl<>(resultToList, pageable, conditionCodePage.getTotalElements());
        } else {
            return findConditions(pageable);
        }

    }


    public List<Condition> readCondition(Page<ConditionCode> result) {
        List<Condition> results = new ArrayList<>();
        for (ConditionCode conditionCode : result.getContent()) {
            results.add(new Condition(
                    conditionCode.getId(),
                    conditionCode.getConditionShortNm(),
                    conditionCode.getProgAreaCd(),
                    conditionCode.getFamilyCd(),
                    conditionCode.getCoinfectionGrpCd(),
                    conditionCode.getNndInd(),
                    conditionCode.getInvestigationFormCd(),
                    conditionCode.getStatusCd()));
        }
        return results;
    }


    public List<Condition> findAllConditions() {
        var conditions = conditionCodeRepository.findAll(Sort.by(Sort.Direction.ASC, "conditionShortNm"));
        return conditions.stream()
                .map(c -> new Condition(
                        c.getId(),
                        c.getConditionShortNm(),
                        c.getProgAreaCd(),
                        c.getFamilyCd(),
                        c.getCoinfectionGrpCd(),
                        c.getNndInd(),
                        c.getInvestigationFormCd(),
                        c.getStatusCd()))
                .toList();
    }
}
