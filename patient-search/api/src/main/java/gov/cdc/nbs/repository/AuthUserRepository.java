package gov.cdc.nbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.odse.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, QuerydslPredicateExecutor<AuthUser> {
    public Optional<AuthUser> findByUserId(String userId);

    public Optional<AuthUser> findByNedssEntryId(Long nedssEntryId);

    @Query("SELECT au FROM AuthUser au WHERE au.id in (SELECT role.authUserUid FROM AuthUserRole role WHERE role.progAreaCd IN :programAreas)")
    public Page<AuthUser> findByProgramAreas(@Param("programAreas") List<String> programAreas, Pageable pageable);
}
