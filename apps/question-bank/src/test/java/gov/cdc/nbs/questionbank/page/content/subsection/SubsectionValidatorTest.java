package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.ValidateSubsectionException;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubsectionValidatorTest {

  @Mock private EntityManager entityManager;

  @InjectMocks SubSectionValidator subSectionValidator;

  private static final Long SECTION = 1015l;
  private static final Long SUB_SECTION = 1016l;
  public static final Long ROLLINGNOTE = 1019l;
  public static final Long SINGLE_SELECT_QUESTION = 1007L;

  @Test
  void not_valid_subsection_no_page_found() {
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);
    assertThrows(
        PageNotFoundException.class, () -> subSectionValidator.validateIfCanBeGrouped(1l, 100l));
  }

  @Test
  void not_valid_subsection_no_subsection_found() {
    WaTemplate page = new WaTemplate();
    long subsectionId = 100l;
    page.setUiMetadata(Collections.emptyList());
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    ValidateSubsectionException exception =
        assertThrows(
            ValidateSubsectionException.class,
            () -> subSectionValidator.validateIfCanBeGrouped(1l, subsectionId));
    assertEquals(("Failed to find subsection with id: " + subsectionId), exception.getMessage());
  }

  @Test
  void not_valid_subsection_include_core_question() {
    WaTemplate page = new WaTemplate();
    long subsectionId = 100l;
    int question1 = 1;
    List<WaUiMetadata> pageElements = getPageElements();
    page.setUiMetadata(pageElements);

    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);

    pageElements.get(question1).setDataLocation("Person_name.first_nm");
    ValidateSubsectionException exception =
        assertThrows(
            ValidateSubsectionException.class,
            () -> subSectionValidator.validateIfCanBeGrouped(1l, subsectionId));
    assertEquals(
        "Subsection includes questions that are considered 'core'", exception.getMessage());

    pageElements.get(question1).setDataLocation(null);
    ValidateSubsectionException exception2 =
        assertThrows(
            ValidateSubsectionException.class,
            () -> subSectionValidator.validateIfCanBeGrouped(1l, subsectionId));
    assertEquals(
        "Subsection includes questions that are considered 'core'", exception2.getMessage());
  }

  @Test
  void not_valid_subsection_include_published_question() {
    WaTemplate page = new WaTemplate();
    long subsectionId = 100l;
    int question1 = 1, question2 = 2;
    List<WaUiMetadata> pageElements = getPageElements();
    pageElements.get(question1).setPublishIndCd('T');
    pageElements.get(question1).setDataLocation("test_ANSWER_TXT_test");
    pageElements.get(question2).setPublishIndCd('F');
    pageElements.get(question2).setDataLocation("test_ANSWER_TXT_test");
    page.setUiMetadata(pageElements);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    ValidateSubsectionException exception =
        assertThrows(
            ValidateSubsectionException.class,
            () -> subSectionValidator.validateIfCanBeGrouped(1l, subsectionId));
    assertEquals(
        "Subsection includes a question(s) that has already been published.",
        exception.getMessage());
  }

  @Test
  void not_valid_subsection_include_not_only_rolling_question() {
    WaTemplate page = new WaTemplate();
    long subsectionId = 100l;
    int question1 = 1, question2 = 2;
    List<WaUiMetadata> pageElements = getPageElements();
    pageElements.get(question1).setDataLocation("test_ANSWER_TXT_test");
    pageElements.get(question2).setDataLocation("test_ANSWER_TXT_test");
    pageElements.get(question1).setNbsUiComponentUid(ROLLINGNOTE);
    page.setUiMetadata(pageElements);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    ValidateSubsectionException exception =
        assertThrows(
            ValidateSubsectionException.class,
            () -> subSectionValidator.validateIfCanBeGrouped(1l, subsectionId));
    assertEquals(
        """
        Subsection can only have the Repeating Note field \
        and no other fields in the repeating block subsection.\
        """,
        exception.getMessage());
  }

  @Test
  void valid_Subsection() {
    WaTemplate page = new WaTemplate();
    long subsectionId = 100l;
    int question1 = 1, question2 = 2;
    List<WaUiMetadata> pageElements = getPageElements();
    pageElements.get(question1).setDataLocation("test_ANSWER_TXT_test");
    pageElements.get(question2).setDataLocation("test_ANSWER_TXT_test");
    pageElements.get(question1).setPublishIndCd(null);
    pageElements.get(question2).setPublishIndCd('F');
    page.setUiMetadata(pageElements);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    subSectionValidator.validateIfCanBeGrouped(1l, subsectionId);
  }

  @Test
  void valid_Subsection_include_only_rolling_question() {
    WaTemplate page = new WaTemplate();
    long subsectionId = 100l;
    int question1 = 1, question2 = 2;
    List<WaUiMetadata> pageElements = new ArrayList<>(getPageElements());
    pageElements.remove(question2);
    pageElements.get(question1).setDataLocation("test_ANSWER_TXT_test");
    pageElements.get(question1).setPublishIndCd(null);
    pageElements.get(question1).setNbsUiComponentUid(ROLLINGNOTE);
    page.setUiMetadata(pageElements);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    subSectionValidator.validateIfCanBeGrouped(1l, subsectionId);
  }

  List<WaUiMetadata> getPageElements() {
    WaUiMetadata subsection = new WaUiMetadata(100l, 50, SUB_SECTION);
    WaUiMetadata question1 = new WaUiMetadata(200l, 51, SINGLE_SELECT_QUESTION);
    WaUiMetadata question2 = new WaUiMetadata(300l, 52, SINGLE_SELECT_QUESTION);
    WaUiMetadata section = new WaUiMetadata(101l, 53, SECTION);
    return Arrays.asList(subsection, question1, question2, section);
  }
}
