package gov.cdc.nbs.authentication;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;
import gov.cdc.nbs.authentication.util.AuthObjectUtil;
import gov.cdc.nbs.exception.BadTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private Algorithm algorithm;

    @Mock
    private AuthUserRepository repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserPermissionFinder permissionFinder;

    @Mock
    private UserAuthorizationVerifier verifier;

    @Spy
    private SecurityProperties properties = new SecurityProperties(
            "secret",
            "test-issuer",
            10000);

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_return_user_details_by_username() {
        // Mock
        AuthUser authUser = AuthObjectUtil.authUser();
        when(repository.findByUserId("test")).thenReturn(Optional.of(authUser));
        when(algorithm.sign(Mockito.any(), Mockito.any())).thenReturn("SomeBytes".getBytes());
        when(permissionFinder.getUserPermissions(Mockito.any())).thenReturn(AuthObjectUtil.authorities());

        // method in test
        NbsUserDetails userDetails = service.loadUserByUsername("test");

        // assertions
        assertNotNull(userDetails);
        assertEquals(authUser.getUserFirstNm(), userDetails.getFirstName());
        assertEquals(authUser.getUserLastNm(), userDetails.getLastName());
        assertEquals(authUser.getUserId(), userDetails.getUsername());
        assertEquals(authUser.getMasterSecAdminInd().equals('T'), userDetails.isMasterSecurityAdmin());
        assertEquals(authUser.getProgAreaAdminInd().equals('T'), userDetails.isProgramAreaAdmin());
        assertTrue(authUser.getAdminProgramAreas().stream()
                .allMatch(a -> userDetails.getAdminProgramAreas().contains(a.getProgAreaCd())));
        assertEquals(authUser.getAudit().getRecordStatusCd().equals(AuthRecordStatus.ACTIVE), userDetails.isEnabled());
    }

    @Test
    void should_return_user_details_by_token() {
        // Mock
        AuthUser authUser = AuthObjectUtil.authUser();
        when(repository.findByUserId("test")).thenReturn(Optional.of(authUser));
        when(algorithm.sign(Mockito.any(), Mockito.any())).thenReturn("SomeBytes".getBytes());
        when(permissionFinder.getUserPermissions(Mockito.any())).thenReturn(AuthObjectUtil.authorities());
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        when(decodedJWT.getSubject()).thenReturn("test");

        // method in test
        NbsUserDetails userDetails = service.findUserByToken(decodedJWT);

        // assertions
        assertNotNull(userDetails);
        assertEquals(authUser.getUserFirstNm(), userDetails.getFirstName());
        assertEquals(authUser.getUserLastNm(), userDetails.getLastName());
        assertEquals(authUser.getUserId(), userDetails.getUsername());
        assertEquals(authUser.getMasterSecAdminInd().equals('T'), userDetails.isMasterSecurityAdmin());
        assertEquals(authUser.getProgAreaAdminInd().equals('T'), userDetails.isProgramAreaAdmin());
        assertTrue(authUser.getAdminProgramAreas().stream()
                .allMatch(a -> userDetails.getAdminProgramAreas().contains(a.getProgAreaCd())));
        assertEquals(authUser.getAudit().getRecordStatusCd().equals(AuthRecordStatus.ACTIVE), userDetails.isEnabled());
    }

    @Test
    void should_not_return_user_details_by_token() {
        // Mock
        when(repository.findByUserId("test")).thenReturn(Optional.empty());
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        when(decodedJWT.getSubject()).thenReturn("test");

        // method in test
        assertThrows(BadTokenException.class, () -> service.findUserByToken(decodedJWT));
    }

    @Test
    void should_not_return_user_details() {
        // Mock
        when(repository.findByUserId("test")).thenReturn(Optional.empty());

        // method in test
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("test"));
    }

    @Test
    void should_delegate_user_authorization_to_verifier() {
        service.isAuthorized(1L, "PERMISSION");

        verify(verifier).isAuthorized(1L, "PERMISSION");
    }

    @Test
    void should_be_authorized_user_details() {
        var userDetails = AuthObjectUtil.userDetails();
        assertTrue(service.isAuthorized(userDetails, AuthObjectUtil.AUTHORITY));
    }

    @Test
    void should_not_be_authorized_user_details() {
        var userDetails = AuthObjectUtil.userDetails();
        assertFalse(service.isAuthorized(userDetails, "different-permission"));
    }



}
