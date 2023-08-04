package gov.cdc.nbs.questionbank.condition;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



class ConditionCreatorTest {

    @Mock
    ConditionCodeRepository conditionCodeRepository;

    @Mock
    LdfPageSetRepository ldfPageSetRepository;

    @InjectMocks
    ConditionCreator conditionCreator;

    private static final long userId = 1L;
    public ConditionCreatorTest(){

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createConditionTest() {
        CreateConditionRequest request = getCreateConditionRequest();
        ConditionCode conditionDb = new ConditionCode(conditionCreator.conditionAdd(request, userId));
        when(conditionCodeRepository.save(Mockito.any(ConditionCode.class))).thenReturn(conditionDb);
        when(conditionCodeRepository.checkId(Mockito.anyString())).thenReturn(0L);
        when(conditionCodeRepository.checkConditionName(Mockito.anyString())).thenReturn(0L);
        CreateConditionResponse response = conditionCreator.createCondition(request, userId);
        assertEquals(conditionDb.getId(), response.getId());
        assertEquals(HttpStatus.CREATED, response.getStatus());
    }

    @Test
    void createIdExistsTest() {
        CreateConditionRequest request = new CreateConditionRequest();
        request.setId("1L");
        when(conditionCodeRepository.checkId(Mockito.anyString())).thenReturn((1L));
        CreateConditionResponse response = conditionCreator.createCondition(request, userId);
        assertEquals(null, response.getId());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void createConditionNameExistsTest() {
        CreateConditionRequest request = new CreateConditionRequest();
        request.setConditionShortNm("condition name");
        when(conditionCodeRepository.checkConditionName(Mockito.anyString())).thenReturn(1l);
        CreateConditionResponse response = conditionCreator.createCondition(request, userId);
        assertEquals(null, response.getId());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void checkIdExists() {
        when(conditionCodeRepository.checkId(Mockito.anyString())).thenReturn(1L);
        boolean val = conditionCreator.checkId("1L");
        assertTrue(val);
    }

    @Test
    void checkIdExistsNullTest() {
        boolean val = conditionCreator.checkId(null);
        assertFalse(val);
    }

    @Test
    void checkConditionNameExists() {
        when(conditionCodeRepository.checkConditionName(Mockito.anyString())).thenReturn(1L);
        boolean val = conditionCreator.checkConditionNm("condition name");
        assertTrue(val);
    }

    @Test
    void checkConditionNameExistsNullTest() {
        boolean val = conditionCreator.checkConditionNm(null);
        assertFalse(val);
    }

    @Test
    void testFindDisplayRow() {
        int maxDisplayRow = 10;
        doReturn(maxDisplayRow).when(ldfPageSetRepository).findMaxDisplayRow();
        int result = conditionCreator.findDisplayRow();
        assertEquals(maxDisplayRow + 1, result);
    }

    @Test
    void testGenerateNewIdWithNoExistingIds() {
        doReturn(new ArrayList<>()).when(ldfPageSetRepository).findAllIds();
        String newId = conditionCreator.getLdfId();
        assertEquals("2023001", newId);
    }

    @Test
    void testGenerateNewIdWithExistingIdsContainingCurrentYear() {
        List<String> existingIds = Arrays.asList("2023001", "2023002", "2023003");
        doReturn(existingIds).when(ldfPageSetRepository).findAllIds();
        String newId = conditionCreator.getLdfId();
        assertEquals("2023004", newId);
    }

    @Test
    void testGenerateNewIdWithExistingIdsNotContainingCurrentYear() {
        List<String> existingIds = Arrays.asList("2022001", "98", "99");
        doReturn(existingIds).when(ldfPageSetRepository).findAllIds();
        String newId = conditionCreator.getLdfId();
        assertEquals("2023001", newId);
    }

    private CreateConditionRequest getCreateConditionRequest() {
        CreateConditionRequest request = new CreateConditionRequest();
        request.setId("1L");
        request.setCodeSystemDescTxt("code system");
        request.setConditionShortNm("condition name");
        request.setProgAreaCd("prog test");
        request.setNndInd('Y');
        request.setReportableMorbidityInd('Y');
        request.setReportableSummaryInd('Y');
        request.setContactTracingEnableInd('Y');
        request.setFamilyCd("family test");
        request.setCoinfectionGrpCd("co infection test");

        return request;
    }

}
