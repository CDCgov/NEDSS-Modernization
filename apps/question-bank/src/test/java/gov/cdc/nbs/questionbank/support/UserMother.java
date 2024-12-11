package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthPermSetRepository;
import gov.cdc.nbs.authentication.entity.AuthProgAreaAdmin;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.entity.AuthUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

@Component
@Transactional
public class UserMother {

  @Autowired
  private UserDetailsService userService;

  @Autowired
  private AuthUserRepository userRepository;

  @Autowired
  private AuthPermSetRepository permSetRepository;

  public void adminUser() {
    var admin = userRepository.findByUserId("admin").orElseGet(this::createAdminUser);
    setSecurityContext(admin);
  }

  public void noPermissions() {
    var user = userRepository.findByUserId("empty").orElseGet(this::createEmptyUser);
    setSecurityContext(user);
  }

  private AuthUser createEmptyUser() {
    var now = Instant.now();
    var audit = new AuthAudit(1L, now);
    var user = new AuthUser();

    user.setUserId("empty");
    user.setUserType("internalUser");
    user.setUserFirstNm("test");
    user.setUserLastNm("empty");
    user.setMasterSecAdminInd('F');
    user.setProgAreaAdminInd('F');
    user.setNedssEntryId(2L);
    user.setAudit(audit);
    user.setAuthUserRoles(new ArrayList<>());
    user.setAdminProgramAreas(new ArrayList<>());

    return userRepository.save(user);
  }

  private AuthUser createAdminUser() {
    var now = Instant.now();
    var audit = new AuthAudit(1L, now);

    // Test-db restore has SUPERUSER permission with id 22
    var permissionSet = permSetRepository.findById(22L)
        .orElseThrow(() -> new RuntimeException("Failed to find SUPERUSER permission set"));

    var user = new AuthUser();

    var progAreaAdmin = new AuthProgAreaAdmin();
    progAreaAdmin.setAudit(audit);
    progAreaAdmin.setAuthUserInd('P');
    progAreaAdmin.setAuthUserUid(user);
    progAreaAdmin.setProgAreaCd("STD");

    var role = new AuthUserRole(user, permissionSet)
        .name("SUPERUSER")
        .programArea("STD")
        .jurisdiction("ALL")
        .guest('F')
        .audit(audit);

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
    UserDetails userDetails = userService.loadUserByUsername(user.getUserId());
    SecurityContextHolder.getContext().setAuthentication(
        new PreAuthenticatedAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        )
    );
  }
}
