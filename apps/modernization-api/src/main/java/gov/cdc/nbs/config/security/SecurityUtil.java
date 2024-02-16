package gov.cdc.nbs.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import gov.cdc.nbs.authentication.NbsUserDetails;

public class SecurityUtil {

    private SecurityUtil() {}

    public static NbsUserDetails getUserDetails() {
        return (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
