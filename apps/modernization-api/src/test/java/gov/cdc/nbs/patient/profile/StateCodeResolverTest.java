package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.srte.StateCode;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StateCodeResolverTest {
    @Test
    void should_resolve_state_code_for_postal_locator() {
        StateCodeResolver resolver = new StateCodeResolver();
        PostalLocator locator = mock(PostalLocator.class);
        when(locator.getCntryCd()).thenReturn("01");
        Optional<StateCode> actual = resolver.resolve(locator);
        assertThat(actual.get().getStateNm()).isEqualTo("AL");
    }
}
