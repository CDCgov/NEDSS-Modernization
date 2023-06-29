package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Locator;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.entity.srte.StateCode;
import gov.cdc.nbs.repository.StateCodeRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class StateCodeResolverTest {

    @Test
    void should_lookup_state_when_a_postal_locator_has_a_state() {

        StateCodeRepository repository = mock(StateCodeRepository.class);

        StateCode stateCode = mock(StateCode.class);

        when(repository.findById(anyString())).thenReturn(Optional.of(stateCode));

        PostalLocator locator = mock(PostalLocator.class);
        when(locator.getStateCd()).thenReturn("state");

        StateCodeResolver resolver = new StateCodeResolver(repository);

        Optional<StateCode> actual = resolver.resolve(locator);

        assertThat(actual).contains(stateCode);

        verify(repository).findById("state");
    }

    @Test
    void should_not_lookup_state_when_a_postal_locator_does_not_has_a_state() {

        StateCodeRepository repository = mock(StateCodeRepository.class);

        PostalLocator locator = mock(PostalLocator.class);

        StateCodeResolver resolver = new StateCodeResolver(repository);

        Optional<StateCode> actual = resolver.resolve(locator);

        assertThat(actual).isNotPresent();

        verifyNoInteractions(repository);
    }

    @Test
    void should_not_lookup_state_without_a_postal_locator() {

        StateCodeRepository repository = mock(StateCodeRepository.class);

        Locator locator = mock(TeleLocator.class);

        StateCodeResolver resolver = new StateCodeResolver(repository);

        Optional<StateCode> actual = resolver.resolve(locator);

        assertThat(actual).isNotPresent();

        verifyNoInteractions(repository);
    }
}
