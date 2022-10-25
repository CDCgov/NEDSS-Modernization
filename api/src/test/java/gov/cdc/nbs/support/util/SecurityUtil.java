package gov.cdc.nbs.support.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.entity.odse.AuthUser;

public class SecurityUtil {

    /*
     * Adds the specified user to the spring security context
     */
    public static void setSecurityContext(AuthUser user) {
        var nbsUserDetails = new NbsUserDetails(user.getUserId(), null, user.getId(), null, null);
        var pat = new PreAuthenticatedAuthenticationToken(nbsUserDetails, null, nbsUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(pat);
    }
}
