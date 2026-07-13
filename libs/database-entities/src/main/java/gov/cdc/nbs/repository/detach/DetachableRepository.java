package gov.cdc.nbs.repository.detach;

import org.springframework.stereotype.Repository;

@Repository
public interface DetachableRepository<T> {
  void detach(T t);
}
