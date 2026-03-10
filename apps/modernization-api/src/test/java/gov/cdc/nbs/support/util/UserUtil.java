package gov.cdc.nbs.support.util;

import gov.cdc.nbs.authentication.NbsUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

  public static Long getCurrentUserId() {
    return ((NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getId();
  }
}
