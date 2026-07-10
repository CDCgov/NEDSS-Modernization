package gov.cdc.nbs.repository;

public interface DetachableRepository<T> {
  void detach(T t);
}
