package gov.cdc.nbs.support.util;

import org.springframework.security.core.context.SecurityContextHolder;
import gov.cdc.nbs.authentication.NbsUserDetails;

public class UserUtil {

    public static Long getCurrentUserId() {
        return ((NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getId();
    }
}
