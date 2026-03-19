package gov.cdc.nbs.authentication.util;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthProgAreaAdmin;
import gov.cdc.nbs.authentication.entity.AuthUser;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AuthObjectUtil {
  public static AuthUser authUser() {
    var user = new AuthUser();
    user.setId(1L);
    user.setUserFirstNm("test");
    user.setUserLastNm("user");
    user.setMasterSecAdminInd('T');
    user.setProgAreaAdminInd('T');
    user.setAdminProgramAreas(AuthObjectUtil.progAreaAdmins(user));
    user.setUserId("test");
    user.setAudit(audit());
    return user;
  }

  public static AuthAudit audit() {
    var now = Instant.now();
    return new AuthAudit(999L, now);
  }

  public static List<AuthProgAreaAdmin> progAreaAdmins(AuthUser user) {
    var adminAreas = new ArrayList<AuthProgAreaAdmin>();
    adminAreas.add(new AuthProgAreaAdmin(null, "progArea", user, 'T', audit()));
    return adminAreas;
  }
}
