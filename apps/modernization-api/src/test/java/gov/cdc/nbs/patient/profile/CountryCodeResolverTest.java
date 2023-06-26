package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.srte.CountryCode;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CountryCodeResolverTest {
    @Test
    void should_resolve_state_code_for_postal_locator() {
        CountryCodeResolver resolver = new CountryCodeResolver();
        PostalLocator locator = mock(PostalLocator.class);
        when(locator.getCntryCd()).thenReturn("840");
        Optional<CountryCode> actual = resolver.resolve(locator);
        assertThat(actual.get().getCodeDescTxt()).isEqualTo("United States");
    }
}
