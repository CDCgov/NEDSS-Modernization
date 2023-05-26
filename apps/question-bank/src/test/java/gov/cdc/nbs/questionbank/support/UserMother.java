package gov.cdc.nbs.questionbank.support;

import java.time.Instant;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthPermSetRepository;
import gov.cdc.nbs.authentication.entity.AuthProgAreaAdmin;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.entity.AuthUserRole;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;

@Component
@Transactional
public class UserMother {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthUserRepository userRepository;

    @Autowired
    private AuthPermSetRepository permSetRepository;

    public AuthUser adminUser() {
        var admin = userRepository.findByUserId("admin").orElseGet(() -> createAdminUser());
        setSecurityContext(admin);
        return admin;
    }

    private AuthUser createAdminUser() {
        var now = Instant.now();
        var audit = new AuthAudit();
        audit.setAddTime(now);
        audit.setAddUserId(1L);
        audit.setLastChgTime(now);
        audit.setLastChgUserId(1L);
        audit.setRecordStatusCd(AuthRecordStatus.ACTIVE);
        audit.setRecordStatusTime(now);

        // Test-db restore has SUPERUSER permission with id 22
        var permissionSet = permSetRepository.findById(22L)
                .orElseThrow(() -> new RuntimeException("Failed to find SUPERUSER permission set"));

        var user = new AuthUser();

        var progAreaAdmin = new AuthProgAreaAdmin();
        progAreaAdmin.setAudit(audit);
        progAreaAdmin.setAuthUserInd('P');
        progAreaAdmin.setAuthUserUid(user);
        progAreaAdmin.setProgAreaCd("STD");

        var role = new AuthUserRole();
        role.setAudit(audit);
        role.setAuthUserUid(user);
        role.setAuthPermSetUid(permissionSet);
        role.setProgAreaCd("STD");
        role.setJurisdictionCd("ALL");
        role.setRoleGuestInd('F');
        role.setAuthRoleNm("SUPERUSER");


        user.setUserId("admin");
        user.setUserType("internalUser");
        user.setUserFirstNm("test");
        user.setUserLastNm("admin");
        user.setMasterSecAdminInd('T');
        user.setProgAreaAdminInd('T');
        user.setNedssEntryId(1L);
        user.setAudit(audit);
        user.setAuthUserRoles(Collections.singletonList(role));
        user.setAdminProgramAreas(Collections.singletonList(progAreaAdmin));
        return userRepository.save(user);
    }

    private void setSecurityContext(AuthUser user) {
        NbsUserDetails userDetails = userService.loadUserByUsername(user.getUserId());
        SecurityContextHolder.getContext().setAuthentication(
                new PreAuthenticatedAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()));
    }
}
