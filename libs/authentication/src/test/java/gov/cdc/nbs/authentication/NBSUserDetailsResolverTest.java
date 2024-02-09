package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthProgAreaAdmin;
import gov.cdc.nbs.authentication.entity.AuthUser;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NBSUserDetailsResolverTest {

    @Test
    void should_resolve_details_from_authorized_user() {

        UserPermissionFinder finder = mock(UserPermissionFinder.class);

        when(finder.getUserPermissions(any())).thenReturn(Set.of());


        AuthUser user = new AuthUser();
        user.setId(17L);
        user.setUserFirstNm("first-name-value");
        user.setUserLastNm("last-name-value");
        user.setMasterSecAdminInd('F');
        user.setProgAreaAdminInd('F');
        user.setUserId("user-id-value");
        user.setAudit(new AuthAudit(227L, Instant.parse("2024-09-03T07:09:11.00Z")));
        user.setAdminProgramAreas(
            List.of(
                new AuthProgAreaAdmin(
                    null,
                    "program-area",
                    user,
                    'T',
                    null
                )
            )
        );

        NBSUserDetailsResolver resolver = new NBSUserDetailsResolver(finder);

        NbsUserDetails resolved = resolver.resolve(user);

        assertThat(resolved)
            .returns("first-name-value", NbsUserDetails::getFirstName)
            .returns("last-name-value", NbsUserDetails::getLastName)
            .returns("user-id-value", NbsUserDetails::getUsername)
            .returns(true, NbsUserDetails::isEnabled)
        ;


        verify(finder).getUserPermissions(user);
    }

    @Test
    void should_resolve_disabled_details_from_authorized_user_when_user_is_inactive() {

        UserPermissionFinder finder = mock(UserPermissionFinder.class);

        when(finder.getUserPermissions(any())).thenReturn(Set.of());

        AuthAudit audit = new AuthAudit(
            227L,
            Instant.parse("2024-09-03T07:09:11.00Z")
        ).inactivate(Instant.parse("2024-09-10T00:00:00Z"));


        AuthUser user = new AuthUser();
        user.setId(17L);
        user.setUserFirstNm("first-name-value");
        user.setUserLastNm("last-name-value");
        user.setMasterSecAdminInd('T');
        user.setProgAreaAdminInd('F');
        user.setUserId("user-id-value");
        user.setAudit(audit);
        user.setAdminProgramAreas(List.of());

        NBSUserDetailsResolver resolver = new NBSUserDetailsResolver(finder);

        NbsUserDetails resolved = resolver.resolve(user);

        assertThat(resolved.isEnabled()).isFalse();

    }

    @Test
    void should_resolve_details_with_authorities_from_permission_finder() {

        UserPermissionFinder finder = mock(UserPermissionFinder.class);

        when(finder.getUserPermissions(any())).thenReturn(
            Set.of(
                new NbsAuthority(
                    "business-operation-value",
                    "business-object-value",
                    "program-area-value",
                    "authority-value"
                    )
            )
        );

        AuthUser user = new AuthUser();
        user.setId(17L);
        user.setUserFirstNm("first-name-value");
        user.setUserLastNm("last-name-value");
        user.setMasterSecAdminInd('T');
        user.setProgAreaAdminInd('F');
        user.setUserId("user-id-value");
        user.setAudit(new AuthAudit(227L, Instant.parse("2024-09-03T07:09:11.00Z")));
        user.setAdminProgramAreas(List.of());

        NBSUserDetailsResolver resolver = new NBSUserDetailsResolver(finder);

        NbsUserDetails resolved = resolver.resolve(user);

        assertThat(resolved.getAuthorities())
            .satisfiesExactly(
                actual -> assertThat(actual)
                    .returns("business-object-value", NbsAuthority::object)
                    .returns("business-operation-value", NbsAuthority::operation)
                    .returns("program-area-value", NbsAuthority::programArea)
                    .returns("authority-value", NbsAuthority::getAuthority)
            );


        verify(finder).getUserPermissions(user);
    }

}
