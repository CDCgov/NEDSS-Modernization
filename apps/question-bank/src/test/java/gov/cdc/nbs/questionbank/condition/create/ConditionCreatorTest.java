package gov.cdc.nbs.questionbank.condition.create;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.condition.ConditionCreator;
import gov.cdc.nbs.questionbank.condition.exception.ConditionCreateException;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.exception.NullObjectException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConditionCreatorTest {

  @Mock ConditionCodeRepository conditionCodeRepository;

  @Mock LdfPageSetRepository ldfPageSetRepository;

  @Spy Clock clock = Clock.fixed(Instant.parse("2024-01-09T02:03:04Z"), ZoneId.of("UTC"));

  @InjectMocks ConditionCreator conditionCreator;

  private static final long USER_ID = 1L;

  @Test
  void createConditionTest() {
    CreateConditionRequest request = getCreateConditionRequest();
    ConditionCode conditionDb =
        new ConditionCode(conditionCreator.conditionAdd(request, USER_ID, 123));
    when(conditionCodeRepository.save(Mockito.any(ConditionCode.class))).thenReturn(conditionDb);
    when(conditionCodeRepository.checkId(Mockito.anyString())).thenReturn(0L);
    when(conditionCodeRepository.checkConditionName(Mockito.anyString())).thenReturn(0L);
    Condition response = conditionCreator.createCondition(request, USER_ID);
    assertEquals(conditionDb.getId(), response.id());
  }

  @Test
  void requireNonNullCode() {
    CreateConditionRequest request =
        new CreateConditionRequest(
            null, "null", "null", "null", null, null, null, null, null, null);
    assertThrows(
        NullObjectException.class, () -> conditionCreator.createCondition(request, USER_ID));
  }

  @Test
  void requireNonNullCodeSystem() {
    CreateConditionRequest request =
        new CreateConditionRequest(
            "null", null, "null", "null", null, null, null, null, null, null);

    assertThrows(
        NullObjectException.class, () -> conditionCreator.createCondition(request, USER_ID));
  }

  @Test
  void requireNonNullName() {
    CreateConditionRequest request =
        new CreateConditionRequest(
            "null", "null", null, "null", null, null, null, null, null, null);

    assertThrows(
        NullObjectException.class, () -> conditionCreator.createCondition(request, USER_ID));
  }

  @Test
  void requireNonNullProgramArea() {
    CreateConditionRequest request =
        new CreateConditionRequest(
            "null", "null", "null", null, null, null, null, null, null, null);

    assertThrows(
        NullObjectException.class, () -> conditionCreator.createCondition(request, USER_ID));
  }

  @Test
  void createIdExistsTest() {
    CreateConditionRequest request =
        new CreateConditionRequest(
            "1L", "null", "null", "null", null, null, null, null, null, null);
    when(conditionCodeRepository.checkId(Mockito.anyString())).thenReturn((1L));
    assertThrows(
        ConditionCreateException.class, () -> conditionCreator.createCondition(request, USER_ID));
  }

  @Test
  void createConditionNameExistsTest() {
    CreateConditionRequest request =
        new CreateConditionRequest(
            "null", "null", "condition name", "null", null, null, null, null, null, null);
    when(conditionCodeRepository.checkConditionName(Mockito.anyString())).thenReturn(1L);
    assertThrows(
        ConditionCreateException.class, () -> conditionCreator.createCondition(request, USER_ID));
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
    String currentYear = String.valueOf(LocalDate.now(clock).getYear());
    String expected = currentYear + "001";
    assertEquals(expected, newId);
  }

  @Test
  void testGenerateNewIdWithExistingIdsContainingCurrentYear() {
    String currentYear = String.valueOf(LocalDate.now(clock).getYear());
    List<String> existingIds =
        Arrays.asList(currentYear + "001", currentYear + "002", currentYear + "003");
    doReturn(existingIds).when(ldfPageSetRepository).findAllIds();
    String newId = conditionCreator.getLdfId();
    String expected = currentYear + "004";
    assertEquals(expected, newId);
  }

  @Test
  void testGenerateNewIdWithExistingIdsNotContainingCurrentYear() {
    List<String> existingIds = Arrays.asList("2022001", "98", "99");
    doReturn(existingIds).when(ldfPageSetRepository).findAllIds();
    String newId = conditionCreator.getLdfId();
    String currentYear = String.valueOf(LocalDate.now(clock).getYear());
    String expected = currentYear + "001";
    assertEquals(expected, newId);
  }

  private CreateConditionRequest getCreateConditionRequest() {
    return new CreateConditionRequest(
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
  }
}
