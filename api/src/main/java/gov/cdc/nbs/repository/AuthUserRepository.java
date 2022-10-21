package gov.cdc.nbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.odse.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, QuerydslPredicateExecutor<AuthUser> {

    @Query("SELECT au FROM AuthUser au where au.userId = :userId")
    public Optional<AuthUser> findByUserId(@Param("userId") String userId);
}
