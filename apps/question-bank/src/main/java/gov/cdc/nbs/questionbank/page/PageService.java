package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.function.Consumer;

@Component
public class PageService {

  private final EntityManager entityManager;

  public PageService(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public void with(final long identifier, final Consumer<With<WaTemplate>> consumer) {
    WaTemplate page = this.entityManager.find(WaTemplate.class, identifier);
    With<WaTemplate> with = new With<>(page);
    consumer.accept(with);
  }

  public static class With<E> {
    private final E entity;

    private With(final E entity) {
      this.entity = entity;
    }

    public With<E> use(final Consumer<E> consumer) {
      consumer.accept(this.entity);
      return this;
    }

  }

}
