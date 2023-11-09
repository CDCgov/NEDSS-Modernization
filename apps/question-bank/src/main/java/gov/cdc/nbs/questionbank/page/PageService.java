package gov.cdc.nbs.questionbank.page;

import javax.persistence.EntityManager;

public class PageService {

  private final EntityManager entityManager;

  public PageService(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }
}
