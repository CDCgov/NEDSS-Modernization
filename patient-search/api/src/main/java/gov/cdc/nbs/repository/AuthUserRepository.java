package gov.cdc.nbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, QuerydslPredicateExecutor<AuthUser> {
    public Optional<AuthUser> findByUserId(String userId);

    public Optional<AuthUser> findByNedssEntryId(Long nedssEntryId);
}
