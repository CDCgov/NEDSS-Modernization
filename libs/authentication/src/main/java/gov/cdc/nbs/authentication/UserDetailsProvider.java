package gov.cdc.nbs.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsProvider {

    public NbsUserDetails getCurrentUserDetails() {
        return (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
