package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.util.AuthObjectUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthorizationVerifierTest {

    @Mock
    AuthUserRepository repository;

    @Mock
    UserPermissionFinder finder;

    @InjectMocks
    UserAuthorizationVerifier verifier;

    @Test
    void should_be_authorized_user_id() {
        // Mock
        AuthUser user = new AuthUser();
        user.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(finder.getUserPermissions(Mockito.any())).
            thenReturn(
                Set.of(
                    new NbsAuthority(
                        "BUSINESS_OPERATION",
                        "BUSINESS_OBJECT",
                        "programArea",
                        123,
                        "jurisdiction",
                        "AUTHORITY"
                    )
                )
            );

        // test
        assertTrue(verifier.isAuthorized(1L, "AUTHORITY"));
    }

    @Test
    void should_not_be_authorized_user_id() {
        // Mock
        when(repository.findById(1L)).thenReturn(Optional.of(AuthObjectUtil.authUser()));
        when(finder.getUserPermissions(Mockito.any())).thenReturn(new HashSet<>());

        // test
        assertFalse(verifier.isAuthorized(1L, "different-permission"));
    }

}
