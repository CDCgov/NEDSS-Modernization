package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class TestPageCleaner {

  private final EntityManager entityManager;



  TestPageCleaner(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  void clean(final PageIdentifier page) {
    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());
    if (found != null) {
      this.entityManager.remove(found);
    }
  }
}
