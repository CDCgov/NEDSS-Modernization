package gov.cdc.nbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.odse.AuthUserRole;

public interface AuthUserRoleRepository extends JpaRepository<AuthUserRole, Long> {

    @Query("SELECT role FROM AuthUserRole role WHERE role.authUserUid.id=:userId AND role.authPermSetUid.id=:permissionId AND role.progAreaCd=:programArea")
    Optional<AuthUserRole> findByUserAndPermissionSet(@Param("userId") Long userId,
            @Param("permissionId") Long permissionId, @Param("programArea") String programArea);
}
