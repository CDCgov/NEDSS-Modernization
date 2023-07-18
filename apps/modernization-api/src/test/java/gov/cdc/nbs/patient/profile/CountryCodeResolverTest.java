package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Locator;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.entity.srte.CountryCode;
import gov.cdc.nbs.repository.CountryCodeRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class CountryCodeResolverTest {

    @Test
    void should_lookup_country_when_a_postal_locator_has_a_country() {

        CountryCodeRepository repository = mock(CountryCodeRepository.class);

        CountryCode countryCode = mock(CountryCode.class);

        when(repository.findById(anyString())).thenReturn(Optional.of(countryCode));

        PostalLocator locator = mock(PostalLocator.class);
        when(locator.getCntryCd()).thenReturn("country");

        CountryCodeResolver resolver = new CountryCodeResolver(repository);

        Optional<CountryCode> actual = resolver.resolve(locator);

        assertThat(actual).contains(countryCode);

        verify(repository).findById("country");
    }

    @Test
    void should_not_lookup_country_when_a_postal_locator_does_not_has_a_country() {

        CountryCodeRepository repository = mock(CountryCodeRepository.class);

        PostalLocator locator = mock(PostalLocator.class);

        CountryCodeResolver resolver = new CountryCodeResolver(repository);

        Optional<CountryCode> actual = resolver.resolve(locator);

        assertThat(actual).isNotPresent();

        verifyNoInteractions(repository);
    }

    @Test
    void should_not_lookup_country_without_a_postal_locator() {

        CountryCodeRepository repository = mock(CountryCodeRepository.class);

        Locator locator = mock(TeleLocator.class);

        CountryCodeResolver resolver = new CountryCodeResolver(repository);

        Optional<CountryCode> actual = resolver.resolve(locator);

        assertThat(actual).isNotPresent();

        verifyNoInteractions(repository);
    }
}
