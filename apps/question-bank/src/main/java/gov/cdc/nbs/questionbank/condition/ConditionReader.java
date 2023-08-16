package gov.cdc.nbs.questionbank.condition;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;


import com.querydsl.core.types.dsl.BooleanExpression;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
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


    public Page<ReadConditionResponse.GetCondition> searchCondition(ReadConditionRequest request, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();
        Predicate filterPredicate = buildFilterPredicate(request);
        if (request.getSearchText() != null && !request.getSearchText().trim().isEmpty()) {
            BooleanExpression searchPredicate = QConditionCode.conditionCode.id.eq(request.getSearchText())
                    .or(QConditionCode.conditionCode.conditionShortNm.containsIgnoreCase(request.getSearchText()));
            predicate.or(searchPredicate);
        }
        predicate.and(filterPredicate);
        Page<ConditionCode> conditionCodePage = conditionCodeRepository.findAll(predicate, pageable);
        List<ReadConditionResponse.GetCondition> resultToList = readCondition(conditionCodePage);
        return new PageImpl<>(resultToList, pageable, conditionCodePage.getTotalElements());
    }

    private Predicate buildFilterPredicate(ReadConditionRequest request) {
        QConditionCode conditionCode = QConditionCode.conditionCode;
        String filterField = request.getFilterField();
        String filterValue = request.getFilterValue();
        String singleCharFilterField = request.getSingleCharFilterField();
        Character singleCharValueField = request.getSingleCharValueField();

        BooleanBuilder filterPredicate = new BooleanBuilder();

        if (filterField != null && filterValue != null) {
            if ("progAreaCd".equalsIgnoreCase(filterField)) {
                filterPredicate.and(conditionCode.progAreaCd.eq(filterValue));
            } else if ("familyCd".equalsIgnoreCase(filterField)) {
                filterPredicate.and(conditionCode.familyCd.eq(filterValue));
            } else if ("coinfectionGrpCd".equalsIgnoreCase(filterField)) {
                filterPredicate.and(conditionCode.coinfectionGrpCd.eq(filterValue));
            } else if ("investigationFormCd".equalsIgnoreCase(filterField)) {
                filterPredicate.and(conditionCode.investigationFormCd.eq(filterValue));
            }
        }
            if (singleCharFilterField != null && singleCharValueField != null) {
                if ("nndInd".equalsIgnoreCase(singleCharFilterField)) {
                    filterPredicate.and(conditionCode.nndInd.eq(singleCharValueField));
                } else if ("statusCd".equalsIgnoreCase(singleCharFilterField)) {
                    filterPredicate.and(conditionCode.statusCd.eq(singleCharValueField));
                }
            }
        return filterPredicate;
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
