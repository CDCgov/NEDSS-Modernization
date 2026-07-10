package gov.cdc.nbs.repository.detach;

public interface DetachableRepository<T> {
  void detach(T t);
}
