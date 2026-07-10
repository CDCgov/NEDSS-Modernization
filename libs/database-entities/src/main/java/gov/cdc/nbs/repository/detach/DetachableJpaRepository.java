package gov.cdc.nbs.repository.detach;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class DetachableJpaRepository<T> implements DetachableRepository<T> {
  @PersistenceContext private EntityManager entityManager;

  @Override
  public void detach(T entity) {
    if (entity != null) {
      entityManager.detach(entity);
    }
  }
}
