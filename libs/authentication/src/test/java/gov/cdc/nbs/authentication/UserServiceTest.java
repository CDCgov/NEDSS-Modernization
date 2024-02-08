package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

}
