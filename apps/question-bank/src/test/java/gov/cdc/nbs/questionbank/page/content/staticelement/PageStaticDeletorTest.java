package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.DeleteStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.DeleteElementRequest;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageStaticDeletorTest {

  @Mock private WaUiMetadataRepository uiMetadatumRepository;

  @Mock private EntityManager entityManager;

  @InjectMocks private PageStaticDeletor pageStaticDeletor;

  @Test
  void should_delete_static_element_from_page() {
    var request = new DeleteElementRequest(123L);

    Long pageId = 321L;
    when(entityManager.find(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

    WaUiMetadata component = new WaUiMetadata();
    component.setOrderNbr(7);

    when(uiMetadatumRepository.findById(request.componentId())).thenReturn(Optional.of(component));

    assertTrue(pageStaticDeletor.deleteStaticElement(pageId, request));
  }

  @Test
  void should_not_delete_as_page_id_null() {
    var request = new DeleteElementRequest(123L);
    Long pageId = null;
    assertThrows(
        DeleteStaticElementException.class,
        () -> pageStaticDeletor.deleteStaticElement(pageId, request));
  }

  @Test
  void should_not_delete_as_page_is_not_found() {
    var request = new DeleteElementRequest(123L);
    Long pageId = 123L;
    when(entityManager.find(WaTemplate.class, pageId)).thenReturn(null);
    assertThrows(
        DeleteStaticElementException.class,
        () -> pageStaticDeletor.deleteStaticElement(pageId, request));
  }

  @Test
  void should_not_delete_if_not_draft() {
    var request = new DeleteElementRequest(123L);

    Long pageId = 321L;
    WaTemplate page = new WaTemplate();
    page.setTemplateType("Published");
    page.setId(pageId);
    when(entityManager.find(WaTemplate.class, pageId)).thenReturn(page);

    assertThrows(
        DeleteStaticElementException.class,
        () -> pageStaticDeletor.deleteStaticElement(pageId, request));
  }
}
