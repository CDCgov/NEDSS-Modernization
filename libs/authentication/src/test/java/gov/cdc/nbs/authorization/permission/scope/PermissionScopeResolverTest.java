package gov.cdc.nbs.authorization.permission.scope;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.Permission;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PermissionScopeResolverTest {

    @BeforeAll
    static void setupUser() {
        NbsUserDetails details = mock(NbsUserDetails.class);
        when(details.getId()).thenReturn(117L);

        PreAuthenticatedAuthenticationToken authentication =
            new PreAuthenticatedAuthenticationToken(
                details,
                null,
                details.getAuthorities()
            );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterAll
    static void resetUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void should_resolve_scope_from_finder() {

        PermissionScopeFinder finder = mock(PermissionScopeFinder.class);

        when(finder.find(anyLong(), any())).thenReturn(Optional.of(PermissionScope.any(19L)));

        PermissionScopeResolver resolver = new PermissionScopeResolver(finder);

        Permission permission = new Permission("operation", "object");

        PermissionScope actual = resolver.resolve(permission);

        assertThat(actual.any()).contains(19L);

        verify(finder).find(117L, permission);

    }

    @Test
    void should_resolve_none_scope_when_no_permissions_found() {
        PermissionScopeFinder finder = mock(PermissionScopeFinder.class);

        when(finder.find(anyLong(), any())).thenReturn(Optional.empty());

        PermissionScopeResolver resolver = new PermissionScopeResolver(finder);

        Permission permission = new Permission("operation", "object");

        PermissionScope actual = resolver.resolve(permission);

        assertThat(actual).isEqualTo(PermissionScope.none());
    }
}
