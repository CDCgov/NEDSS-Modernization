package gov.cdc.nbs.questionbank.page;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.classic.ClassicPublishPagePreparer;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPublishPageRequester;
import gov.cdc.nbs.questionbank.page.exception.PagePublishException;
import gov.cdc.nbs.questionbank.page.publish.PagePublisher;
import gov.cdc.nbs.questionbank.page.request.PagePublishRequest;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagePublisherTest {
  @InjectMocks private PagePublisher pageUpdater;

  @Mock private EntityManager entityManager;

  @Mock private ClassicPublishPagePreparer publishPagePreparer;

  @Mock private ClassicPublishPageRequester publishPageRequester;

  @Test
  void testPublishPage() {

    WaTemplate page = new WaTemplate();
    page.setTemplateType(PageConstants.DRAFT);
    page.setId(213L);

    PagePublishRequest request = new PagePublishRequest("something");

    when(entityManager.find(WaTemplate.class, 123L)).thenReturn(page);

    doNothing().when(publishPagePreparer).prepare(213L);

    doNothing().when(publishPageRequester).request(request.versionNotes());

    WaTemplate newPage = new WaTemplate();
    newPage.setTemplateType(PageConstants.PUBLISHED);
    newPage.setId(213L);

    when(entityManager.find(WaTemplate.class, 213L)).thenReturn(newPage);

    pageUpdater.publishPage(123L, request);
    verify(entityManager).find(WaTemplate.class, 213l);
  }

  @Test
  void testPublishDoesNotWorkOnPublished() {
    WaTemplate page = new WaTemplate();
    page.setTemplateType(PageConstants.PUBLISHED);
    page.setId(123L);

    when(entityManager.find(WaTemplate.class, 123L)).thenReturn(page);

    PagePublishRequest request = new PagePublishRequest("something");

    assertThrows(PagePublishException.class, () -> pageUpdater.publishPage(123L, request));
  }

  @Test
  void testIfClassicFailed() {
    WaTemplate page = new WaTemplate();
    page.setTemplateType(PageConstants.DRAFT);
    page.setId(213L);

    PagePublishRequest request = new PagePublishRequest("something");

    when(entityManager.find(WaTemplate.class, 123L)).thenReturn(page);

    doNothing().when(publishPagePreparer).prepare(213L);

    doNothing().when(publishPageRequester).request(request.versionNotes());

    WaTemplate newPage = new WaTemplate();
    newPage.setTemplateType(PageConstants.DRAFT);
    newPage.setId(213L);

    when(entityManager.find(WaTemplate.class, 213L)).thenReturn(newPage);

    assertThrows(PagePublishException.class, () -> pageUpdater.publishPage(123L, request));
  }
}
