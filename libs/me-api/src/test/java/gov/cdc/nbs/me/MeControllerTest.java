package gov.cdc.nbs.me;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.PermissionFinder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MeControllerTest {

    @Test
    void should_resolve_permissions_from_finder() {



        PermissionFinder finder = mock(PermissionFinder.class);

        when(finder.find(anyLong())).thenReturn(
            List.of(
                new Permission("object-one", "operation-one"),
                new Permission("object-two", "operation-two")
            )
        );

        NbsUserDetails user = mock(NbsUserDetails.class);

        when(user.getId()).thenReturn(2371L);
        when(user.getFirstName()).thenReturn("first-name");
        when(user.getLastName()).thenReturn("last-name");

        MeController controller = new MeController(finder);

        Me actual = controller.me(user);

        assertThat(actual)
            .returns(2371L, Me::identifier)
            .returns("first-name", Me::firstName)
            .returns("last-name", Me::lastName)
            .extracting(Me::permissions)
            .asList()
            .contains("object-one-operation-one", "object-two-operation-two")
            ;

    }
}
