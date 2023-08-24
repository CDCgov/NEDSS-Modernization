package gov.cdc.nbs.questionbank.condition.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.questionbank.condition.ConditionCreator;
import gov.cdc.nbs.questionbank.condition.exception.ConditionCreateException;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;



class ConditionCreatorTest {

    @Mock
    ConditionCodeRepository conditionCodeRepository;

    @Mock
    LdfPageSetRepository ldfPageSetRepository;

    @InjectMocks
    ConditionCreator conditionCreator;

    private static final long userId = 1L;

    public ConditionCreatorTest() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createConditionTest() {
        CreateConditionRequest request = getCreateConditionRequest();
        ConditionCode conditionDb = new ConditionCode(conditionCreator.conditionAdd(request, userId, 123));
        when(conditionCodeRepository.save(Mockito.any(ConditionCode.class))).thenReturn(conditionDb);
        when(conditionCodeRepository.checkId(Mockito.anyString())).thenReturn(0L);
        when(conditionCodeRepository.checkConditionName(Mockito.anyString())).thenReturn(0L);
        Condition response = conditionCreator.createCondition(request, userId);
        assertEquals(conditionDb.getId(), response.id());
    }

    @Test
    void createIdExistsTest() {
        CreateConditionRequest request =
                new CreateConditionRequest(
                        "1L",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        when(conditionCodeRepository.checkId(Mockito.anyString())).thenReturn((1L));
        assertThrows(ConditionCreateException.class, () -> conditionCreator.createCondition(request, userId));
    }

    @Test
    void createConditionNameExistsTest() {
        CreateConditionRequest request =
                new CreateConditionRequest(
                        null,
                        null,
                        "condition name",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        when(conditionCodeRepository.checkConditionName(Mockito.anyString())).thenReturn(1l);
        assertThrows(ConditionCreateException.class, () -> conditionCreator.createCondition(request, userId));
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
        CreateConditionRequest request = new CreateConditionRequest(
                "1",
                "code system",
                "condition name",
                "prog test",
                'Y',
                'Y',
                'Y',
                'Y',
                "family test",
                "co infection test");
        return request;
    }

}
