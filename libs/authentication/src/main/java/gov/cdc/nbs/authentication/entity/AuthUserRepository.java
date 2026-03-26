package gov.cdc.nbs.authentication.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
  Optional<AuthUser> findByUserId(String userId);
}
