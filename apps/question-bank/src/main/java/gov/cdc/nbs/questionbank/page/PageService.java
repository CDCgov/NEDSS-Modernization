package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import jakarta.persistence.EntityManager;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PageService {

  private final EntityManager entityManager;

  public PageService(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * Runs the {@code consumer} if an entity can be uniquely identified by the given {@code
   * identifier}.
   *
   * @param identifier The unique identifier of the Page
   * @param consumer A consumer that will run on the found Page
   */
  @Transactional
  public void using(final long identifier, final Consumer<WaTemplate> consumer) {
    WaTemplate page = this.entityManager.find(WaTemplate.class, identifier);
    if (page != null) {
      consumer.accept(page);
    } else {
      throw new PageNotFoundException(identifier);
    }
  }
}
