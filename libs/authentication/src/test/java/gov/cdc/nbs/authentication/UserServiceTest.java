package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.util.AuthObjectUtil;
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
    NBSUserDetailsResolver resolver;

    @InjectMocks
    private UserService service;

    @Test
    void should_return_user_details_by_username() {
        // Mock
        AuthUser authUser = mock(AuthUser.class);

        when(repository.findByUserId(any())).thenReturn(Optional.of(authUser));

        NbsUserDetails details = mock(NbsUserDetails.class);

        when(resolver.resolve(any())).thenReturn(details);


        // method in test
        NbsUserDetails userDetails = service.loadUserByUsername("test");

        // assertions
        assertThat(userDetails).isSameAs(details);

        verify(repository).findByUserId("test");

        verify(resolver).resolve(authUser);
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
