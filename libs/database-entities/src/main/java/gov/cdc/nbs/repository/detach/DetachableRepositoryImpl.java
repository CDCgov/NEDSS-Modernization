package gov.cdc.nbs.repository.detach;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class DetachableRepositoryImpl<T> implements DetachableRepository<T> {
  @PersistenceContext private EntityManager entityManager;

  // Required constructor
  //  public DetachableJpaRepository(
  //      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
  //    super(entityInformation, entityManager);
  //    this.entityManager = entityManager;
  //  }

  @Override
  public void detach(T entity) {
    if (entity != null) {
      entityManager.detach(entity);
    }
  }
}
