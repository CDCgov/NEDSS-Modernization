package gov.cdc.nbs.questionbank.page.content.staticelement;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.DeleteStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.DeleteElementRequest;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PageStaticDeletor {
  private final WaUiMetadataRepository uiMetadatumRepository;
  private final EntityManager entityManager;

  public PageStaticDeletor(
      WaUiMetadataRepository uiMetadataRepository, EntityManager entityManager) {
    this.uiMetadatumRepository = uiMetadataRepository;
    this.entityManager = entityManager;
  }

  public boolean deleteStaticElement(Long pageId, DeleteElementRequest request) {
    if (pageId == null) {
      throw new DeleteStaticElementException("Page is required");
    }

    WaTemplate template = entityManager.find(WaTemplate.class, pageId);

    if (template == null) {
      throw new DeleteStaticElementException("Please provide valid page ID");
    }

    if (!("Draft").equals(template.getTemplateType())) {
      throw new DeleteStaticElementException("Page cannot be published");
    }

    WaUiMetadata component =
        uiMetadatumRepository
            .findById(request.componentId())
            .orElseThrow(() -> new DeleteStaticElementException("Failed to find component"));

    uiMetadatumRepository.deleteById(request.componentId());

    uiMetadatumRepository.decrementOrderNbrGreaterThan(pageId, component.getOrderNbr());

    return true;
  }
}
