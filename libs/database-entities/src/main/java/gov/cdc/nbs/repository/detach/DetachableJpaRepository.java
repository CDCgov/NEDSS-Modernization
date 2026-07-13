package gov.cdc.nbs.repository.detach;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class DetachableJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements DetachableRepository<T> {
  @PersistenceContext private EntityManager entityManager;

  // Required constructor
  public DetachableJpaRepository(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @Override
  public void detach(T entity) {
    if (entity != null) {
      entityManager.detach(entity);
    }
  }
}
