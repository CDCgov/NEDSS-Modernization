package gov.cdc.nbs.config.security;

import gov.cdc.nbs.authentication.NbsUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

  private SecurityUtil() {}

  public static NbsUserDetails getUserDetails() {
    return (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
