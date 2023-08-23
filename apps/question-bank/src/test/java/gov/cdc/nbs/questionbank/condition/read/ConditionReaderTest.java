package gov.cdc.nbs.questionbank.condition.read;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.condition.ConditionReader;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;


class ConditionReaderTest {

    @Mock
    ConditionCodeRepository conditionCodeRepository;

    @InjectMocks
    ConditionReader conditionReader;

    ConditionReaderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findConditionsTest() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<ConditionCode> conditionCode = getConditionList(pageable);
        when(conditionCodeRepository.findAll(pageable)).thenReturn(conditionCode);
        Page<Condition> result = conditionReader.findConditions(pageable);
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void searchConditionTest() {
        ReadConditionRequest readConditionRequest = new ReadConditionRequest();
        readConditionRequest.setSearchText("Y2798");

        List<ConditionCode> mockConditionList = new ArrayList<>();
        Pageable pageable = Pageable.unpaged();
        Page<ConditionCode> mockConditionPage = new PageImpl<>(mockConditionList);
        when(conditionCodeRepository.findAll(Mockito.any(BooleanBuilder.class), Mockito.any(Pageable.class)))
                .thenReturn(mockConditionPage);
        Page<Condition> resultPage =
                conditionReader.searchCondition(readConditionRequest, pageable);
        assertNotNull(resultPage);
        assertEquals(mockConditionList.size(), resultPage.getContent().size());
    }

    @Test
    void searchWithException() {
        ReadConditionRequest request = new ReadConditionRequest();
        request.setSearchText("Z57685");
        Pageable pageable = Pageable.unpaged();

        when(conditionCodeRepository.findAll(Mockito.any(BooleanBuilder.class), Mockito.any(Pageable.class)))
                .thenThrow(new RuntimeException("Repository error"));

        assertThrows(RuntimeException.class, () -> conditionReader.searchCondition(request, pageable));
    }

    private Page<ConditionCode> getConditionList(Pageable pageable) {
        List<ConditionCode> conditionCode = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            conditionCode.add(getCondition(i));
        }
        return new PageImpl<>(conditionCode, pageable, conditionCode.size());
    }

    private ConditionCode getCondition(int i) {
        ConditionCode conditionCode = new ConditionCode();
        conditionCode.setConditionCodesetNm("TestingConditionShortNm");
        conditionCode.setId("TEST" + i);
        conditionCode.setProgAreaCd("GCD");
        conditionCode.setFamilyCd("ARBO");
        conditionCode.setCoinfectionGrpCd("STD_HIV_GROUP");
        conditionCode.setNndInd('Y');
        conditionCode.setInvestigationFormCd("INV_FORM_GEN");
        conditionCode.setStatusCd('A');


        return conditionCode;
    }
}

