package gov.cdc.nbs.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.util.AuthObjectUtil;
import gov.cdc.nbs.exception.BadTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    AuthUserRepository repository;

    @Mock
    TokenCreator creator;

    @Mock
    NBSUserDetailsResolver resolver;

    @InjectMocks
    private UserService service;

    @Test
    void should_return_user_details_by_username() {
        // Mock
        AuthUser authUser = mock(AuthUser.class);
        when(authUser.getUserId()).thenReturn("authenticated-user");

        when(repository.findByUserId(any())).thenReturn(Optional.of(authUser));

        NbsUserDetails details = mock(NbsUserDetails.class);

        when(resolver.resolve(any(), any())).thenReturn(details);

        when(creator.forUser(any())).thenReturn(new NBSToken("resolved-token"));

        // method in test
        NbsUserDetails userDetails = service.loadUserByUsername("test");

        // assertions
        assertThat(userDetails).isSameAs(details);

        verify(repository).findByUserId("test");

        verify(resolver).resolve(authUser, new NBSToken("resolved-token"));

        verify(creator).forUser("authenticated-user");
    }

    @Test
    void should_return_user_details_by_token() {
        // Mock
        AuthUser authUser = mock(AuthUser.class);
        when(authUser.getUserId()).thenReturn("authenticated-user");

        when(repository.findByUserId(any())).thenReturn(Optional.of(authUser));

        NbsUserDetails details = mock(NbsUserDetails.class);

        when(resolver.resolve(any(), any())).thenReturn(details);

        when(creator.forUser(any())).thenReturn(new NBSToken("resolved-token"));

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(decodedJWT.getSubject()).thenReturn("test");

        // method in test
        NbsUserDetails userDetails = service.findUserByToken(decodedJWT);

        // assertions
        assertThat(userDetails).isSameAs(details);

        verify(repository).findByUserId("test");

        verify(resolver).resolve(authUser, new NBSToken("resolved-token"));

        verify(creator).forUser("authenticated-user");
    }

    @Test
    void should_not_return_user_details_by_token_when_user_not_found() {
        // Mock
        when(repository.findByUserId("test")).thenReturn(Optional.empty());
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(decodedJWT.getSubject()).thenReturn("test");

        // method in test
        assertThrows(BadTokenException.class, () -> service.findUserByToken(decodedJWT));
    }

    @Test
    void should_not_return_user_details_when_user_not_found() {
        // Mock
        when(repository.findByUserId("test")).thenReturn(Optional.empty());

        // method in test
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("test"));
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
