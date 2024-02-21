package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.entity.WaRdbMetadata;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequest;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.question.request.update.UpdateQuestionRequest.DataType;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageQuestionUpdaterTest {


  @Mock
  private EntityManager entityManager;

  @InjectMocks
  private PageQuestionUpdater updater;


  @Test
  void should_update_text_question_from_page() {
    WaTemplate page = new WaTemplate();
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    List<WaUiMetadata> waUiMetadataList = prepareWaUiMetadataList();
    page.setUiMetadata(waUiMetadataList);
    UpdatePageQuestionRequest request = prepareUpdatePageQuestionRequest();

    PagesQuestion textQuestion = updater.updatePageQuestion(1l, 100l, request, 3l);

    boolean required = request.required().equals("T");
    boolean display = request.required().equals("T");
    boolean enabled = request.required().equals("T");
    Assert.assertEquals(required, textQuestion.required());
    Assert.assertEquals(display, textQuestion.display());
    Assert.assertEquals(enabled, textQuestion.enabled());
    assertEquals(request.defaultLabelInReport(), textQuestion.defaultLabelInReport());
  }

  @Test
  void should_update_coded_question_from_page() {
    WaTemplate page = new WaTemplate();
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    List<WaUiMetadata> waUiMetadataList = prepareWaUiMetadataList();
    page.setUiMetadata(waUiMetadataList);
    UpdatePageQuestionRequest request = prepareUpdatePageQuestionRequest();

    PagesQuestion codedQuestion = updater.updatePageQuestion(1l, 200l, request, 3l);

    boolean required = request.required().equals("T");
    boolean display = request.required().equals("T");
    boolean enabled = request.required().equals("T");

    Assert.assertEquals(required, codedQuestion.required());
    Assert.assertEquals(display, codedQuestion.display());
    Assert.assertEquals(enabled, codedQuestion.enabled());
    assertEquals(request.defaultLabelInReport(), codedQuestion.defaultLabelInReport());
  }

  @Test
  void should_update_date_question_from_page() {
    WaTemplate page = new WaTemplate();
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    List<WaUiMetadata> waUiMetadataList = prepareWaUiMetadataList();
    page.setUiMetadata(waUiMetadataList);
    UpdatePageQuestionRequest request = prepareUpdatePageQuestionRequest();

    PagesQuestion dateQuestion = updater.updatePageQuestion(1l, 300l, request, 3l);

    boolean required = request.required().equals("T");
    boolean display = request.required().equals("T");
    boolean enabled = request.required().equals("T");

    Assert.assertEquals(required, dateQuestion.required());
    Assert.assertEquals(display, dateQuestion.display());
    Assert.assertEquals(enabled, dateQuestion.enabled());
    assertEquals(request.defaultLabelInReport(), dateQuestion.defaultLabelInReport());
  }

  @Test
  void should_update_numeric_question_from_page() {
    WaTemplate page = new WaTemplate();
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    List<WaUiMetadata> waUiMetadataList = prepareWaUiMetadataList();
    page.setUiMetadata(waUiMetadataList);
    UpdatePageQuestionRequest request = prepareUpdatePageQuestionRequest();

    PagesQuestion numericQuestion = updater.updatePageQuestion(1l, 400l, request, 3l);

    boolean required = request.required().equals("T");
    boolean display = request.required().equals("T");
    boolean enabled = request.required().equals("T");

    Assert.assertEquals(required, numericQuestion.required());
    Assert.assertEquals(display, numericQuestion.display());
    Assert.assertEquals(enabled, numericQuestion.enabled());
    assertEquals(request.defaultLabelInReport(), numericQuestion.defaultLabelInReport());
  }



  @Test
  void should_not_update_question_from_page_no_page_found() {
    UpdatePageQuestionRequest request = prepareUpdatePageQuestionRequest();
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    assertThrows(PageNotFoundException.class, () -> updater.updatePageQuestion(1l, 2l, request, 3l));
  }

  @Test
  void should_not_update_question_from_page_no_question_found() {
    WaTemplate page = new WaTemplate();
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    List<WaUiMetadata> waUiMetadataList = prepareWaUiMetadataList();
    page.setUiMetadata(waUiMetadataList);
    UpdatePageQuestionRequest request = prepareUpdatePageQuestionRequest();

    PageContentModificationException exception =
        assertThrows(PageContentModificationException.class, () -> updater.updatePageQuestion(1l, 7l, request, 3l));

    Assertions.assertEquals("Unable to update a question from a page, the page does not contain the question",
        exception.getMessage());
  }

  private UpdatePageQuestionRequest prepareUpdatePageQuestionRequest() {
    return new UpdatePageQuestionRequest("updatedQuestionLbl", "updatedTooltip",
        "T", "T", "T", "", "100",
        "updatedRptLabel", "updatedDataMart", "updatedAdminComments");
  }

  private List<WaUiMetadata> prepareWaUiMetadataList() {
    List<WaUiMetadata> waUiMetadataList = new ArrayList<>();
    waUiMetadataList.add(getWaUiMetadata(100l, DataType.TEXT.toString()));
    waUiMetadataList.add(getWaUiMetadata(200l, DataType.CODED.toString()));
    waUiMetadataList.add(getWaUiMetadata(300l, DataType.DATE.toString()));
    waUiMetadataList.add(getWaUiMetadata(400l, DataType.NUMERIC.toString()));
    return waUiMetadataList;
  }

  private WaUiMetadata getWaUiMetadata(long id, String dataType) {
    WaUiMetadata waUiMetadata = new WaUiMetadata();
    waUiMetadata.setDataType(dataType);
    waUiMetadata.setId(id);
    waUiMetadata.setQuestionIdentifier("q_identifier_100");
    waUiMetadata.setQuestionToolTip("test");
    waUiMetadata.setQuestionLabel("test");
    waUiMetadata.setEnableInd("F");
    waUiMetadata.setDisplayInd("F");
    waUiMetadata.setEnableInd("F");
    waUiMetadata.setFieldSize("5");
    waUiMetadata.setDefaultValue("test");
    waUiMetadata.setStandardQuestionIndCd('F');
    waUiMetadata.setNbsUiComponentUid(1008l);
    waUiMetadata.setOrderNbr(7);

    WaRdbMetadata waRdbMetadatum = new WaRdbMetadata();
    waRdbMetadatum.setWaUiMetadataUid(waUiMetadata);
    waRdbMetadatum.setRptAdminColumnNm("test");
    waRdbMetadatum.setUserDefinedColumnNm("test");
    waUiMetadata.setWaRdbMetadatum(waRdbMetadatum);
    return waUiMetadata;
  }

}
