package gov.cdc.nbs.authentication;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    private final String token;
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

}
