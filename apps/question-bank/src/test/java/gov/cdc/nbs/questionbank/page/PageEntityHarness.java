package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.function.Consumer;

@Component
public class PageEntityHarness {

  private final EntityManager entityManager;

  public PageEntityHarness(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public Harnessed with(final PageIdentifier identifier) {
    WaTemplate page = this.entityManager.find(WaTemplate.class, identifier.id());
    return new Harnessed(page);
  }

  public static class Harnessed {
    private final WaTemplate entity;

    private Harnessed(final WaTemplate entity) {
      this.entity = entity;
    }

    public Harnessed use(final Consumer<WaTemplate> consumer) {
      consumer.accept(this.entity);
      return this;
    }

  }

}
