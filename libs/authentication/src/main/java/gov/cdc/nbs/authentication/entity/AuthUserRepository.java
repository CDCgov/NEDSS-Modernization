package gov.cdc.nbs.authentication.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, QuerydslPredicateExecutor<AuthUser> {
  public Optional<AuthUser> findByUserId(String userId);

}
