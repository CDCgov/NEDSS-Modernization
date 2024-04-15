package gov.cdc.nbs.me;

import gov.cdc.nbs.authentication.NbsUserDetails;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MeControllerTest {

  @Test
  void should_resolve_permissions_from_authorities() {
    NbsUserDetails user = mock(NbsUserDetails.class);

    when(user.getId()).thenReturn(2371L);
    when(user.getFirstName()).thenReturn("first-name");
    when(user.getLastName()).thenReturn("last-name");
    when(user.getAuthorities()).thenReturn(
        Set.of(
            new SimpleGrantedAuthority("operation-one-object-one"),
            new SimpleGrantedAuthority("operation-two-object-two")
        )
    );

    MeController controller = new MeController();

    Me actual = controller.me(user);

    assertThat(actual)
        .returns(2371L, Me::identifier)
        .returns("first-name", Me::firstName)
        .returns("last-name", Me::lastName)
        .extracting(Me::permissions)
        .asInstanceOf(InstanceOfAssertFactories.LIST) 
        .contains("operation-one-object-one", "operation-two-object-two")
    ;

  }
}
