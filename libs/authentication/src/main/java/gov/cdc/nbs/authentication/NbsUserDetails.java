package gov.cdc.nbs.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Getter
@Builder
@RequiredArgsConstructor
public class NbsUserDetails implements UserDetails {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final boolean isMasterSecurityAdmin;
    private final boolean isProgramAreaAdmin;
    private final Set<String> adminProgramAreas;
    private final String password;
    private final Set<NbsAuthority> authorities;
    private final NBSToken token;
    private final boolean isEnabled;

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonExpired();
    }

    /**
     * Checks if user has the specified permission
     *
     * @param businessObject
     * @param operation
     * @return
     */
    public boolean hasPermission(String businessObject, String operation) {
        if (getAuthorities() == null) {
            return false;
        }
        return getAuthorities()
            .stream()
            .anyMatch(a -> a.getBusinessObject().equals(businessObject)
                && a.getBusinessOperation().equals(operation));
    }

}
